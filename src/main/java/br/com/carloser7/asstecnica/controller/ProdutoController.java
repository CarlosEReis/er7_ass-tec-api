package br.com.carloser7.asstecnica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carloser7.asstecnica.model.Produto;
import br.com.carloser7.asstecnica.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    private List<Produto> ProdutoRepository(String sku) {
        if (StringUtils.hasText(sku)) {
            return this.produtoRepository.findBySku(sku);
        }
        return this.produtoRepository.findAll();
    }
}
