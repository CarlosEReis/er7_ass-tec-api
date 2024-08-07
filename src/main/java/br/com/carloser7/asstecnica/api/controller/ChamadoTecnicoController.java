package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.api.model.input.ChamadoInput;
import br.com.carloser7.asstecnica.api.model.input.ConcluiAvaliacaoItemChamadoInput;
import br.com.carloser7.asstecnica.api.model.input.ContatoInput;
import br.com.carloser7.asstecnica.api.model.input.ItemChamadoInput;
import br.com.carloser7.asstecnica.domain.event.ChamadoCriadoEvent;
import br.com.carloser7.asstecnica.domain.event.RecursoCriadoEvent;
import br.com.carloser7.asstecnica.domain.model.*;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepository;
import br.com.carloser7.asstecnica.domain.repository.projection.ChamadoTecnicoView;
import br.com.carloser7.asstecnica.domain.service.CadastroChamadoTecnicoService;
import br.com.carloser7.asstecnica.domain.service.FluxoChamadoService;
import br.com.carloser7.asstecnica.infra.service.FichaPdfChamadoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/chamados")
public class ChamadoTecnicoController {

    @Autowired private ChamadoTecnicoRepository chamadoTecnicoRepository;
    @Autowired private CadastroChamadoTecnicoService cadastroChamadoTecnicoService;
    @Autowired private ApplicationEventPublisher publisher;
    @Autowired private FluxoChamadoService fluxoChamadoService;
    @Autowired private FichaPdfChamadoService fichaPdfChamadoService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<Page<ChamadoTecnicoView>> pesquisar(String nome, Pageable pageable) {
        if (StringUtils.hasText(nome)) {
            return ResponseEntity.ok(
                this.chamadoTecnicoRepository.findByClienteNomeContaining(nome, pageable)
            );
        }
        return ResponseEntity.ok(this.chamadoTecnicoRepository.findChamadoTecnicoBy(pageable));
    }

    @GetMapping("/{chamadoId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<ChamadoTecnico> buscarPorId(@PathVariable Integer chamadoId) {
        var chamadoTecnico = cadastroChamadoTecnicoService.buscar(chamadoId);
        return ResponseEntity.ok(chamadoTecnico);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public ChamadoTecnico criar(@RequestBody @Valid ChamadoInput chamadoInput, HttpServletResponse response) {
        ChamadoTecnico chamadoTecnico = toDomainObject(chamadoInput);
        ChamadoTecnico chamado = this.cadastroChamadoTecnicoService.criar(chamadoTecnico);

        // TODO: Remove puplicacao de evento de chamado criado
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, chamado.getId()));
        this.publisher.publishEvent(new ChamadoCriadoEvent(this, chamado));

        return chamado;
    }

    @PutMapping("/{chamadoId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public ChamadoTecnico atualizar(@PathVariable Integer chamadoId, @RequestBody ChamadoInput chamadoInput) {

        Optional<ChamadoTecnico> chamadoBanco = this.chamadoTecnicoRepository.findById(chamadoId);

        if (chamadoBanco.isPresent()) {
            ChamadoTecnico novoChamado = this.toDomainObject(chamadoInput);
            chamadoBanco.get().getItens().clear();
            chamadoBanco.get().getItens().addAll(novoChamado.getItens());
            chamadoBanco.get().getItens().forEach(item -> item.setChamadoTecnico(chamadoBanco.get()));

            this.chamadoTecnicoRepository.save(chamadoBanco.get());
        }
        return chamadoBanco.get();
    }

    @GetMapping("/{idChamado}/ficha")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<byte[]> fichaChamadoTecnicoPDF(@PathVariable Integer idChamado) throws JRException {
        byte[] ficha = fichaPdfChamadoService.geraFichaChamdo(idChamado);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(ficha);
    }

    @PostMapping(value = "/{chamadoID}/alteracao-status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retornaStatusChamado(@PathVariable Integer chamadoID, @RequestBody String status) {
        StatusChamadoTecnico statusChamadoTecnico = StatusChamadoTecnico.valueOf(status);
        fluxoChamadoService.retonarStatus(chamadoID, statusChamadoTecnico);
    }

    @PostMapping(value = "/{idChamado}/alteracao-status-item/{idItemChamado}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ChamadoTecnico alteracaoStatusChamadoItem(@PathVariable Integer idChamado, @PathVariable Integer idItemChamado,@RequestBody ConcluiAvaliacaoItemChamadoInput concluiAvaliacao) {
        return fluxoChamadoService.alterarStatusItemChamado(idChamado, idItemChamado, concluiAvaliacao);
    }

    private ChamadoTecnico toDomainObject(ChamadoInput chamadoInput) {
        var chamadoTecnico = new ChamadoTecnico();
        chamadoTecnico.setDataCriacao(LocalDateTime.now());
        chamadoTecnico.setStatus(StatusChamadoTecnico.FILA);
        
        var cliente = new Cliente();
        cliente.setId(chamadoInput.getCliente().getId());
        chamadoTecnico.setCliente(cliente);

        chamadoInput.getItens().forEach(
            (item) -> {
                ItemChamadoTecnico itemChamadoTecnico = toDomainObject(item);
                itemChamadoTecnico.setChamadoTecnico(chamadoTecnico);
                chamadoTecnico.getItens().add(toDomainObject(item));
            }
        );

        chamadoInput.getContatos().forEach( 
            (contatoInput) -> 
                {
                    var c = toDomainObject(contatoInput);
                    c.setCliente(cliente);
                    chamadoTecnico.getContatos().add(c);
                
                }
        );

        return chamadoTecnico;
    }

    private ItemChamadoTecnico toDomainObject(ItemChamadoInput input) {
        var itemChamado = new ItemChamadoTecnico();
        itemChamado.setId(input.getId());

        itemChamado.getProduto().setId(input.getProduto().getId());
        itemChamado.setSerial(input.getSerial());
        itemChamado.setDescricao(input.getDescricao());
        return itemChamado;
    }

    private Contato toDomainObject(ContatoInput contatoInput) {
        Contato contato = new Contato();
        contato.setId(contatoInput.id());
        /**contato.setNome(contatoInput.nome());
        contato.setEmail(contatoInput.email());
        contato.setTelefone(contatoInput.telefone());**/
        return contato;
    }

}
