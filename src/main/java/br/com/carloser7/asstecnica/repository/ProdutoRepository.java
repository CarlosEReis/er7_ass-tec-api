package br.com.carloser7.asstecnica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.carloser7.asstecnica.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Integer>{
    
    List<Produto> findBySku(String sku);
}
