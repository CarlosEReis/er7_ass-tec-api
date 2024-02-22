package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.filter.ProdutoFilter;
import br.com.carloser7.asstecnica.domain.repository.projection.ProdutoResumoProjection;

import java.util.List;

public interface ProdutoRepositoryQueries {

    List<ProdutoResumoProjection> pesquisa(ProdutoFilter produtoFilter);
}
