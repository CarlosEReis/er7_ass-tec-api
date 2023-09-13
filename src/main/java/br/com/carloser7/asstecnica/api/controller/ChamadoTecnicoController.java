package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.api.model.input.ChamadoInput;
import br.com.carloser7.asstecnica.api.model.input.ContatoInput;
import br.com.carloser7.asstecnica.api.model.input.ItemChamadoInput;
import br.com.carloser7.asstecnica.domain.model.*;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepository;
import br.com.carloser7.asstecnica.domain.service.CadastroChamadoTecnicoService;
import br.com.carloser7.asstecnica.domain.repository.projection.ChamadoTecnicoView;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chamados")
public class ChamadoTecnicoController {

    @Autowired
    private ChamadoTecnicoRepository chamadoTecnicoRepository;

    @Autowired
    private CadastroChamadoTecnicoService cadastroChamadoTecnicoService;

    @GetMapping()
    public ResponseEntity<Page<ChamadoTecnicoView>> pesquisar(String nome, Pageable pageable) {
        if (StringUtils.hasText(nome)) {
            return ResponseEntity.ok(
                this.chamadoTecnicoRepository.findByClienteNomeContaining(nome, pageable)
            );
        }
        return ResponseEntity.ok(this.chamadoTecnicoRepository.findChamadoTecnicoBy(pageable));
    }

    @GetMapping("/{chamadoId}")
    public ResponseEntity<ChamadoTecnico> buscarPorId(@PathVariable Integer chamadoId) {
        var chamadoTecnico = cadastroChamadoTecnicoService.buscar(chamadoId);
        return ResponseEntity.ok(chamadoTecnico);
    }

    @PostMapping
    public ChamadoTecnico criar(@RequestBody @Valid ChamadoInput chamadoInput) {
        ChamadoTecnico chamadoTecnico = toDomainObject(chamadoInput);
        chamadoTecnico.getItens().forEach(item -> item.setChamadoTecnico(chamadoTecnico));
        ChamadoTecnico chamadoTecnicoSave = this.chamadoTecnicoRepository.save(chamadoTecnico);
        return chamadoTecnicoSave;
    }

    @PutMapping("/{chamadoId}")
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
    public ResponseEntity<byte[]> fichaChamadoTecnicoPDF(@PathVariable Integer idChamado) throws JRException {
        byte[] ficha = this.cadastroChamadoTecnicoService.relatorioFichaChamadoTecnico(idChamado);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(ficha);
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
        itemChamado.setStatus(StatusItemChamadoTecnico.PENDENTE);
        itemChamado.setSku(input.getSku());
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
