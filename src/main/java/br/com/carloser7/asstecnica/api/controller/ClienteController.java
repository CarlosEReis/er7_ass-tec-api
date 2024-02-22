package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.domain.filter.ClienteFilter;
import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.repository.projection.ClienteResumoProjection;
import br.com.carloser7.asstecnica.domain.service.CadastroClienteService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_GESTOR')")
    public ResponseEntity<Cliente> adicionar(@RequestBody @Valid Cliente cliente, HttpServletResponse response) {
        var clienteSalvo = cadastroClienteService.adicionar(cliente, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_GESTOR', 'ROLE_TECNICO')")
    public Page<ClienteResumoProjection> pesquisar(ClienteFilter filter, Pageable pageable) {
        var clientes = cadastroClienteService.pesquisar(filter);
        return new PageImpl<>(clientes, pageable, clientes.size());
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
