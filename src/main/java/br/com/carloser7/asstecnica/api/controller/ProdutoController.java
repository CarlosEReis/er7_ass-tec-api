package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.api.assembler.ProdutoOutputAssembler;
import br.com.carloser7.asstecnica.api.disassembler.ProdutoInputDisassembler;
import br.com.carloser7.asstecnica.api.model.input.ProdutoInput;
import br.com.carloser7.asstecnica.api.model.output.ProdutoOutput;
import br.com.carloser7.asstecnica.domain.filter.ProdutoFilter;
import br.com.carloser7.asstecnica.domain.model.Produto;
import br.com.carloser7.asstecnica.domain.repository.projection.ProdutoResumoProjection;
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
    @Autowired private ProdutoOutputAssembler produtoOutputAssembler;
    @Autowired private ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
        public Page<ProdutoResumoProjection> pesquisa(ProdutoFilter produtoFilter, Pageable pageable) {
        return produtoService.pequisa(produtoFilter.sku(), produtoFilter.nome(), pageable);
    }

    @GetMapping("/{produtoID}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ProdutoOutput buscarPorId(@PathVariable Integer produtoID) {
        return produtoOutputAssembler.toOutput(produtoService.buscaPorIDOuFalha(produtoID));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public ResponseEntity<ProdutoOutput> criar(@RequestBody ProdutoInput produtoInput, HttpServletResponse response) {
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        ProdutoOutput produtoOutput = produtoOutputAssembler.toOutput(produtoService.adiciona(produto, response));
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoOutput);
    }

    @PutMapping("/{produtoID}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public ProdutoOutput atualizar(@PathVariable Integer produtoID, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produdoDB = produtoService.buscaPorIDOuFalha(produtoID);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produdoDB);
        return produtoOutputAssembler.toOutput(produtoService.atualizar(produdoDB));
    }

}
