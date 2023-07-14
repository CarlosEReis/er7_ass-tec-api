package br.com.carloser7.asstecnica.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.carloser7.asstecnica.model.Cliente;
import br.com.carloser7.asstecnica.repository.ClienteRepository;
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

    @PostMapping
    public ResponseEntity<Cliente> adicionar(@RequestBody @Valid Cliente cliente) {
        var clienteSalvo = this.clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @GetMapping
    public List<Cliente> pesquisar(String nome) {
        if (StringUtils.hasText(nome)) {
            return this.clienteRepository.findByNomeContaining(nome);
        }
        return this.clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        var cliente = this.clienteRepository.findById(id);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
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
