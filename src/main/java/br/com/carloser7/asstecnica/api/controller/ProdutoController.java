package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.domain.model.Produto;
import br.com.carloser7.asstecnica.domain.service.CadastroProdutoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired private CadastroProdutoService produtoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<Page<Produto>> listar(@RequestParam(required = false) String search, Pageable pageable) {
        var result = this.produtoService.pesquisa(search, search, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{produtoID}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public Produto buscarPorId(@PathVariable Integer produtoID) {
        return this.produtoService.buscaPorIDOuFalha(produtoID);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public ResponseEntity<Produto> criar(@RequestBody Produto produto, HttpServletResponse response) {
        produto = this.produtoService.adiciona(produto, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping("/{produtoID}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public Produto atualizar(@PathVariable Integer produtoID, @RequestBody @Valid Produto produto) {
        produto = this.produtoService.atualizar(produtoID, produto);
        return produto;
    }

}
