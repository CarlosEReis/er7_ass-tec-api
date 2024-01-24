package br.com.carloser7.asstecnica.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.carloser7.asstecnica.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Integer>{

    boolean existsBySku(String sku);
    List<Produto> findBySku(String sku);
    Page<Produto> findBySkuContainingOrNomeContaining(String sku, String nome,Pageable pageable);
}
