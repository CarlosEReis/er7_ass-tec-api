package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.domain.event.RecursoCriadoEvent;
import br.com.carloser7.asstecnica.domain.exception.NegocioException;
import br.com.carloser7.asstecnica.domain.exception.ProdutoNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.filter.ProdutoFilter;
import br.com.carloser7.asstecnica.domain.model.Produto;
import br.com.carloser7.asstecnica.domain.repository.ProdutoRepository;
import br.com.carloser7.asstecnica.infra.specification.ProdutoSpecification;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CadastroProdutoService {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private ApplicationEventPublisher publisher;

    public Page<Produto> pequisa(ProdutoFilter produtoFilter, Pageable pageable) {
        var produtoSpec = ProdutoSpecification.comFiltro(produtoFilter);
        return produtoRepository.findAll(produtoSpec, pageable);
    }

    public Produto adiciona(Produto produto, HttpServletResponse response) {
        this.validaSKU(produto.getSku());
        produto = this.produtoRepository.save(produto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, produto.getId()));
        return produto;
    }

    public Produto atualizar(Produto produto) {
        //this.validaSKU(produto.getSku());
        return this.produtoRepository.save(produto);
    }

    public Produto buscaPorIDOuFalha(Integer produtoID) {
        return this.produtoRepository.findById(produtoID)
            .orElseThrow(
                () -> new ProdutoNaoEncontradoException(String.valueOf(produtoID)));
    }

    private void validaSKU(String SKU) {
        boolean jaExisteSKU = this.produtoRepository.existsBySku(SKU);
        if (jaExisteSKU)
            throw new NegocioException(
                String.format("Produto com SKU %s já existe.", SKU));
    }
}
