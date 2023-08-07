package br.com.carloser7.asstecnica.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carloser7.asstecnica.api.model.input.ChamadoInput;
import br.com.carloser7.asstecnica.api.model.input.ItemChamadoInput;
import br.com.carloser7.asstecnica.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.model.Cliente;
import br.com.carloser7.asstecnica.model.ItemChamadoTecnico;
import br.com.carloser7.asstecnica.model.StatusChamadoTecnico;
import br.com.carloser7.asstecnica.model.StatusItemChamadoTecnico;
import br.com.carloser7.asstecnica.projection.ChamadoTecnicoProjection;
import br.com.carloser7.asstecnica.repository.ChamadoTecnicoRepository;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/chamados")
public class ChamadoTecnicoController {

    @Autowired
    private ChamadoTecnicoRepository chamadoTecnicoRepository;

    @GetMapping()
    public List<ChamadoTecnicoProjection> pesquisar(String nome) {
        if (StringUtils.hasText(nome)) {
            return this.chamadoTecnicoRepository.findByClienteNomeContaining(nome);
        }
        return this.chamadoTecnicoRepository.findAllProjectionBy();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoTecnico> buscarPorId(@PathVariable Integer id) {
        var cliente = this.chamadoTecnicoRepository.findById(id);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ChamadoTecnico criar(@RequestBody ChamadoInput chamadoInput) {
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
}
