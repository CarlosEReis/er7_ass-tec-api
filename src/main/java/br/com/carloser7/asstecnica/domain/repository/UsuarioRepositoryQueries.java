package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopUsuarioEstatistica;
import br.com.carloser7.asstecnica.domain.filter.TopTecnicosFilter;

public interface UsuarioRepositoryQueries {

    TopUsuarioEstatistica topUsuarios(TopTecnicosFilter filtro);
}
