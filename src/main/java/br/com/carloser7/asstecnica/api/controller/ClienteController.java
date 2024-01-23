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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_GESTOR')")
    public ResponseEntity<Cliente> adicionar(@RequestBody @Valid Cliente cliente, HttpServletResponse response) {
        cliente.getContatos().forEach(contato -> contato.setCliente(cliente));
        var clienteSalvo = this.clienteRepository.save(cliente);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<Page<ClienteView>> pesquisar(String nome, Pageable pageable) {
        if (StringUtils.hasText(nome)) {
            return ResponseEntity.ok(this.clienteRepository.findByNomeContaining(nome, pageable));
        }
        return ResponseEntity.ok(this.clienteRepository.findAllBy(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        var cliente = cadastroClienteService.buscar(id);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deletar(@PathVariable Integer id) {
        this.cadastroClienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
