package br.com.carloser7.asstecnica.api.controller;

import java.util.List;
import java.util.Optional;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import br.com.carloser7.asstecnica.domain.model.Produto;
import br.com.carloser7.asstecnica.domain.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<Page<Produto>> listar(@RequestParam(required = false) String search, Pageable pageable) {
       // if (StringUtils.hasText(sku)) {
            var result = this.produtoRepository.findBySkuContainingOrNomeContaining(search, search, pageable);
        //}
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR', 'ROLE_TECNICO')")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Integer id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.isPresent() ? ResponseEntity.ok(produto.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto produtoSalvo = this.produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GESTOR')")
    public Produto atualizar(@PathVariable Integer id, @RequestBody Produto produto) {
        Produto produdoBanco = this.produtoRepository
            .findById(id)
            .orElseThrow(() -> 
                new EmptyResultDataAccessException("Produto com o id " + id + " não foi encontrado", 1));

        BeanUtils.copyProperties(produto, produdoBanco, "id");
        return this.produtoRepository.save(produdoBanco);
    }

}
