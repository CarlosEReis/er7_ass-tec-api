package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopUsuarios;
import br.com.carloser7.asstecnica.domain.filter.TopTecnicosFilter;

import java.util.List;

public interface UsuarioRepositoryQueries {

    List<TopUsuarios> topUsuarios(TopTecnicosFilter filter);
}
