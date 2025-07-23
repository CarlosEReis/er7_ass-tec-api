package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.api.disassembler.ContatoInputDisassembler;
import br.com.carloser7.asstecnica.api.model.input.ContatoInput;
import br.com.carloser7.asstecnica.domain.model.Contato;
import br.com.carloser7.asstecnica.domain.service.CadastroContatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/{clienteId}/contatos")
public class ContatoController {

    private final CadastroContatoService cadastroContatoService;
    private final ContatoInputDisassembler contatoInputDisassembler;

    public ContatoController(CadastroContatoService cadastroContatoService, ContatoInputDisassembler contatoInputDisassembler){
        this.cadastroContatoService = cadastroContatoService;
        this.contatoInputDisassembler = contatoInputDisassembler;
    }

    @GetMapping
    public ResponseEntity<List<Contato>> listar(@PathVariable Integer clienteId) {
        var contatos = cadastroContatoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(contatos);
    }

    @PostMapping
    public ResponseEntity<Contato> adiciona(@PathVariable Integer clienteId, @RequestBody ContatoInput contatoInput) {
        var novoContato = contatoInputDisassembler.toDomainObject(contatoInput);
        var contato = cadastroContatoService.salvar(clienteId, novoContato);
        return ResponseEntity.status(HttpStatus.CREATED).body(contato); // TODO: adicionar location no header da resposta
    }

    @PutMapping("/{contatoId}")
    public ResponseEntity<Contato> atualiza(@PathVariable Integer clienteId, @PathVariable Integer contatoId, @RequestBody ContatoInput contatoInput) {
        var contatoAtualizado = contatoInputDisassembler.toDomainObject(contatoInput);
        var contato = cadastroContatoService.atualiza(clienteId, contatoId, contatoAtualizado);
        return ResponseEntity.ok(contato);
    }
}
