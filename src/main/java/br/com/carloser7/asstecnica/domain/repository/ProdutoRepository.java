package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Integer>, ProdutoRepositoryQueries {

    boolean existsBySku(String sku);
    List<Produto> findBySku(String sku);
    //Page<Produto> findBySkuContainingOrNomeContaining(String sku, String nome,Pageable pageable);
}
