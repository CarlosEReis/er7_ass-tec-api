package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.domain.event.RecursoCriadoEvent;
import br.com.carloser7.asstecnica.domain.service.CadastroClienteService;
import br.com.carloser7.asstecnica.domain.repository.projection.ClienteView;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.repository.ClienteRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Cliente> adicionar(@RequestBody @Valid Cliente cliente, HttpServletResponse response) {
        cliente.getContatos().forEach(contato -> contato.setCliente(cliente));
        var clienteSalvo = this.clienteRepository.save(cliente);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }
    @GetMapping
    public ResponseEntity<Page<ClienteView>> pesquisar(String nome, Pageable pageable) {
        if (StringUtils.hasText(nome)) {
            return ResponseEntity.ok(this.clienteRepository.findByNomeContaining(nome, pageable));
        }
        return ResponseEntity.ok(this.clienteRepository.findAllBy(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        var cliente = cadastroClienteService.buscar(id);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Integer id) {
        var cliente = this.clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        this.clienteRepository.delete(cliente.get());
        return ResponseEntity.noContent().build();
    }
}
