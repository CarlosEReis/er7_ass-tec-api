package br.com.carloser7.asstecnica.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.carloser7.asstecnica.model.Produto;
import br.com.carloser7.asstecnica.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/{sku}")
    public List<Produto> listar(@PathVariable String sku) {
        if (StringUtils.hasText(sku)) {
            return this.produtoRepository.findBySku(sku);
        }
        return this.produtoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto produtoSalvo = this.produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Integer id, @RequestBody Produto produto) {
        Produto produdoBanco = this.produtoRepository
            .findById(id)
            .orElseThrow(() -> 
                new EmptyResultDataAccessException("Produto com o id " + id + " não foi encontrado", 1));

        BeanUtils.copyProperties(produto, produdoBanco, "id");
        return this.produtoRepository.save(produdoBanco);
    }
}
