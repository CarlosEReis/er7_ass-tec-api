package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.model.Produto;
import br.com.carloser7.asstecnica.domain.repository.projection.ProdutoResumoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Integer>{

    boolean existsBySku(String sku);
    List<Produto> findBySku(String sku);
    Page<ProdutoResumoProjection> findBySkuContainingOrNomeContaining(String sku, String nome, Pageable pageable);

    Page<ProdutoResumoProjection> findAllBy(Pageable pageable);
}
