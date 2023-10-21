package br.com.carloser7.asstecnica.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.carloser7.asstecnica.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Integer>{
    
    List<Produto> findBySku(String sku);
    List<Produto> findBySkuContaining(String sku);
}
