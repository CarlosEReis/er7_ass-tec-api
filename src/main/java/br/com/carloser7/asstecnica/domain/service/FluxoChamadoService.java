package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.api.model.input.ConcluiAvaliacaoItemChamadoInput;
import br.com.carloser7.asstecnica.domain.event.ChamadoConcluidoEvent;
import br.com.carloser7.asstecnica.domain.event.ChamadoProcessandoEvent;
import br.com.carloser7.asstecnica.domain.exception.AlteracaoStatusNaoPermitidaException;
import br.com.carloser7.asstecnica.domain.exception.ItemChamadoNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.model.ItemChamadoTecnico;
import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;
import br.com.carloser7.asstecnica.domain.model.Usuario;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class FluxoChamadoService {

    @Autowired private CadastroChamadoTecnicoService chamadoService;
    @Autowired private ChamadoTecnicoRepository chamadoRepository;
    @Autowired private ApplicationEventPublisher eventPublisher;

    public ChamadoTecnico alterarStatusItemChamado(Integer idChamado, Integer idItemChamado, ConcluiAvaliacaoItemChamadoInput concluiAvaliacao) {
        var status = concluiAvaliacao.getStatus();
        var posicaoTecnica = concluiAvaliacao.getPosicaoTecnica();

        ChamadoTecnico chamado = chamadoService.buscar(idChamado);
        ItemChamadoTecnico item = chamado.getItens().stream()
                .filter(itemAtual -> idItemChamado.equals(itemAtual.getId()))
                .findFirst()
                .orElseThrow(() ->
                        new ItemChamadoNaoEncontradoException(idItemChamado)
                );

        switch (status) {
            case PENDENTE ->
                    throw new AlteracaoStatusNaoPermitidaException(
                            String.format("Item do chamado %s série %s, não pode ser alterado para o status %s, pois está em %s",
                                    item.getProduto().getSku(), item.getSerial(), status, item.getUltimoStatus()));
            case AVALIANDO -> iniciarAvaliacaoItemChamado(item);
            case AVALIADO -> concluirAvaliacaoItemChamado(item, posicaoTecnica);
            default -> throw new IllegalStateException("Unexpected value: " + status);
        }

        return this.chamadoRepository.save(chamado);
    }

    @Transactional
    public void retonarStatus(Integer chamadoID, StatusChamadoTecnico status) {
        Assert.notNull(chamadoID,"chamadoID, não pode ser nulo");
        Assert.notNull(status, "status não pode ser nulo");

        var chamado = chamadoService.buscar(chamadoID);

        if (StatusChamadoTecnico.PROCESSANDO.equals(status) && chamado.estaFinalizado()) {
            // altera status do chamado para PROCESSANDO e altera os itens para AVALIANDO
            // TODO: Ponto de refatoracao

            chamado.enviarParaProcessamento(getUsuarioAtual());
            chamado.getItens().forEach(itemChamadoTecnico -> {
                itemChamadoTecnico.avaliando(getUsuarioAtual());
            });
            return;
        } else if (StatusChamadoTecnico.FILA.equals(status) && chamado.estaEmProcessamento() ) {
            // altera status do chamado para FILA e altera os itens para PENDETE
            // TODO: Ponto de refatoracao

            chamado.enviarParaFila(getUsuarioAtual());
            chamado.getItens().forEach(itemChamadoTecnico -> {
                itemChamadoTecnico.pendente(getUsuarioAtual());
            });
            return;
        } else {
            throw new AlteracaoStatusNaoPermitidaException(
                    String.format("Não é possível alterar o status do chamado de %s para %s", chamado.getStatus(), status));
        }

    }

    private void iniciarAvaliacaoItemChamado(ItemChamadoTecnico item) {
        var idChamado = item.getChamadoTecnico().getId();
        ChamadoTecnico chamado = chamadoService.buscar(idChamado);
        item.avaliando(getUsuarioAtual());

        if (chamado.estaNaFila()){
            chamado.enviarParaProcessamento(getUsuarioAtual());
            this.eventPublisher.publishEvent(new ChamadoProcessandoEvent(this, chamado));
        }
    }

    private void concluirAvaliacaoItemChamado(ItemChamadoTecnico item, String posicaoTecnica) {
        var idChamado = item.getChamadoTecnico().getId();
        ChamadoTecnico chamado = chamadoService.buscar(idChamado);

        item.avaliado(getUsuarioAtual());
        item.setPosicaoTecnica(posicaoTecnica);

        if (chamado.todosItemAvaliados() || chamado.estaEmProcessamento()) {
            chamado.finalizar(getUsuarioAtual());
        }
        chamadoRepository.flush();
        this.eventPublisher.publishEvent(new ChamadoConcluidoEvent(this,chamado));
    }

    private String getUsuarioAtual() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return  usuario.getNome();
    }
}
