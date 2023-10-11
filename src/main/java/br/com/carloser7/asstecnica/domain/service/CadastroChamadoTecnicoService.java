package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.api.model.input.ConcluiAvaliacaoItemChamadoInput;
import br.com.carloser7.asstecnica.domain.exception.AlteracaoStatusNaoPermitidaException;
import br.com.carloser7.asstecnica.domain.exception.ChamadoTecnicoNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.exception.ItemChamadoNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.model.*;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CadastroChamadoTecnicoService {

    @Autowired
    private ChamadoTecnicoRepository chamadoRepository;

    public ChamadoTecnico buscar(Integer chamadoId) {
        return this.chamadoRepository
            .findById(chamadoId)
            .orElseThrow(
                () -> new ChamadoTecnicoNaoEncontradoException(chamadoId));
    }

    public ChamadoTecnico criar(ChamadoTecnico chamado) {

        chamado.getItens().forEach(item -> {
            item.setChamadoTecnico(chamado);

            var statusItem = new StatusItemChamadoObject();
            statusItem.setStatus(StatusItemChamadoTecnico.PENDENTE);
            statusItem.setDataStatus(LocalDateTime.now());
            statusItem.setNomeUsuario(getUsuarioAtual().getNome());
            statusItem.setItemChamado(item);

            item.getStatus().add(statusItem);
        });

        chamado.setStatus(StatusChamadoTecnico.FILA);

        // TODO: Ponto de refatoracao
        var status = new StatusChamadoObject();
        status.setStatus(StatusChamadoTecnico.FILA);
        status.setDataStatus(LocalDateTime.now());
        status.setNomeUsuario(getUsuarioAtual().getNome());
        status.setchamadoTecnico(chamado);

        chamado.getStatusList().add(status);

        return this.chamadoRepository.save(chamado);
    }

    private Usuario getUsuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) authentication.getDetails();
    }

    public ChamadoTecnico alterarStatusChamado(Integer idChamado, StatusChamadoTecnico status) {
        return switch (status) {
            case PROCESSANDO -> iniciarAvaliacaoChamado(idChamado);
            case FINALIZADO -> concluirAvaliacaoChamado(idChamado);
            default -> throw new AlteracaoStatusNaoPermitidaException("Não é possível atualizar o chamado para o status " + status);
        };
   }

    private ChamadoTecnico concluirAvaliacaoChamado(Integer idChamado) {
        // BUSCAR CHAMADO
        ChamadoTecnico chamado = buscar(idChamado);

        // VERIFICAR SE ESTÁ COM O STATUS PROCESSANDO
        var ultimoStatus = chamado.getUltimoStatus();
        if (!ultimoStatus.equals(StatusChamadoTecnico.PROCESSANDO)) {
            throw new AlteracaoStatusNaoPermitidaException(
                    String.format("Não é possível concluir um chamado com o status %s.", ultimoStatus.getDescricao().toUpperCase()));
        }

        // VERIFICAR SE TODOS OS ITENS ESTÃO CONCLUÍDOS (AVALIADOS)
        // CASO NAO LANCAR EXCEPTION
        chamado.getItens().forEach( item -> {
            if (!item.getUltimoStatus().equals(StatusItemChamadoTecnico.AVALIADO)) {
                throw new AlteracaoStatusNaoPermitidaException(
                    String.format(
                        "Não é possível concluir o chamado. O item %s de série %s ainda não foi avaliado.",
                        item.getSku(), item.getSerial())
                );
            }
        });

        // CASO SIM GERAR STATUS E SETAR NO CHAMADO
        var status = new StatusChamadoObject();
        status.setStatus(StatusChamadoTecnico.PROCESSANDO);
        status.setDataStatus(LocalDateTime.now());
        status.setNomeUsuario(getUsuarioAtual().getNome());

        status.setchamadoTecnico(chamado);

        chamado.getStatusList().add(status);

        return this.chamadoRepository.save(chamado);
    }

    private ChamadoTecnico iniciarAvaliacaoChamado(Integer idChamado) {
        ChamadoTecnico chamado = buscar(idChamado);
        var ultimoStatus = chamado.getUltimoStatus();

        if (!ultimoStatus.equals(StatusChamadoTecnico.FILA)) {
            throw new AlteracaoStatusNaoPermitidaException(
                String.format("Não é possível iniciar a avaliação de um chamado com o status %s.", ultimoStatus.getDescricao().toUpperCase())
            );
        }

        chamado.setStatus(StatusChamadoTecnico.PROCESSANDO);

        // TODO: Ponto de refatoracao
        var status = new StatusChamadoObject();
        status.setStatus(StatusChamadoTecnico.PROCESSANDO);
        status.setDataStatus(LocalDateTime.now());
        status.setNomeUsuario(getUsuarioAtual().getNome());
        status.setchamadoTecnico(chamado);

        chamado.getStatusList().add(status);

        return this.chamadoRepository.save(chamado);
    }

    public ChamadoTecnico alterarStatusItemChamado(Integer idChamado, Integer idItemChamado, ConcluiAvaliacaoItemChamadoInput concluiAvaliacao) {
        var status = concluiAvaliacao.getStatus();
        var posicaoTecnica = concluiAvaliacao.getPosicaoTecnica();

        ChamadoTecnico chamado = buscar(idChamado);
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
                        item.getSku(), item.getSerial(), status, item.getUltimoStatus()));
            case AVALIANDO -> iniciarAvaliacaoItemChamado(item);
            case AVALIADO -> concluirAvaliacaoItemChamado(item, posicaoTecnica);
            default -> throw new IllegalStateException("Unexpected value: " + status);
        }

        return this.chamadoRepository.save(chamado);
    }

    private void iniciarAvaliacaoItemChamado(ItemChamadoTecnico item) {
        var ultimoStatus = item.getUltimoStatus();

        //System.out.println("\n\nALTERAR O CHAMADO PARA PROCESSANDO\t\n\n\n"+item.getChamadoTecnico().getId());
        //iniciarAvaliacaoChamado(item.getChamadoTecnico().getId());
        var idChamado = item.getChamadoTecnico().getId();
        ChamadoTecnico chamado = buscar(idChamado);
        if (chamado.getUltimoStatus().equals(StatusChamadoTecnico.FILA)){
            chamado.setStatus(StatusChamadoTecnico.PROCESSANDO);

            // TODO: Ponto de refatoracao
            var status = new StatusChamadoObject();
            status.setStatus(StatusChamadoTecnico.PROCESSANDO);
            status.setDataStatus(LocalDateTime.now());
            status.setNomeUsuario(getUsuarioAtual().getNome());
            status.setchamadoTecnico(chamado);

            chamado.getStatusList().add(status);
        }

        switch (ultimoStatus) {
            case PENDENTE -> {
                item.getStatus().add(
                    new StatusItemChamadoObject(
                        LocalDateTime.now(), getUsuarioAtual().getNome(), StatusItemChamadoTecnico.AVALIANDO, item));

                // TODO: Ao ininciar a avaliação de um item, se o chamado estiver como 'FILA' ele deve ir automaticamento para processando.
            }
            case AVALIANDO -> throw new AlteracaoStatusNaoPermitidaException(
                String.format("Item do chamado %s série %s, já está sendo avaliado.", item.getSku(), item.getSerial()));

            case AVALIADO -> throw new AlteracaoStatusNaoPermitidaException(
                String.format("Item do chamado %s série %s, já foi avaliado.", item.getSku(), item.getSerial()));

            default -> throw new AlteracaoStatusNaoPermitidaException("Alteração de status inválida.");
        }
    }

    private void concluirAvaliacaoItemChamado(ItemChamadoTecnico item, String posicaoTecnica) {
        var ultimoStatus = item.getUltimoStatus();

        switch (ultimoStatus) {
            case PENDENTE -> throw new AlteracaoStatusNaoPermitidaException(
                String.format("Não é possível concluir avaliação do item %s serie %s. O mesmo ainda está com o status %s.",
                    item.getSku(), item.getSerial(), ultimoStatus));

            case AVALIANDO -> {
                item.getStatus().add(
                        new StatusItemChamadoObject(
                                LocalDateTime.now(), getUsuarioAtual().getNome(), StatusItemChamadoTecnico.AVALIADO, item));
                item.setPosicaoTecnica(posicaoTecnica);
            }
            case AVALIADO -> throw new AlteracaoStatusNaoPermitidaException(
                String.format("Não é possível concluir avaliação do item %s serie %s. O mesmo já está com o status %s.",
                    item.getSku(), item.getSerial(), ultimoStatus));

            default -> throw new AlteracaoStatusNaoPermitidaException("Alteração de status inválida.");

        }

    }
    public byte[] relatorioFichaChamadoTecnico (Integer idChamado) throws JRException {
        ChamadoTecnico chamadoTecnico = this.chamadoRepository
            .findById(idChamado)
            .orElseThrow(
                () -> new EmptyResultDataAccessException("Produto com o id " + idChamado + " não foi encontrado", 1));

        List<ChamadoTecnico> chamadoTecnicos = Collections.singletonList(chamadoTecnico);

        InputStream fichaChamado = getClass().getResourceAsStream("/relatorios/ficha-chamado.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(fichaChamado);
        JRSaver.saveObject(jasperReport, "ficha-chamado.jasper");

        InputStream contatos = getClass().getResourceAsStream("/relatorios/ficha-chamado-contatos-embed.jrxml");
        JRSaver.saveObject(JasperCompileManager.compileReport(contatos), "ficha-chamado-contatos-embed.jasper");

        InputStream ocorrencias = getClass().getResourceAsStream("/relatorios/ficha-chamado-ocorrencias-embed.jrxml");
        JRSaver.saveObject(JasperCompileManager.compileReport(ocorrencias), "ficha-chamado-ocorrencias-embed.jasper");

        JasperPrint jasperPrint = JasperFillManager
            .fillReport(jasperReport, null, new JRBeanCollectionDataSource(chamadoTecnicos));
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}

