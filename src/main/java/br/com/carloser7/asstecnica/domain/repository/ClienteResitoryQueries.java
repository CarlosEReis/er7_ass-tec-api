package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopClientesEstatistica;
import br.com.carloser7.asstecnica.domain.filter.ClienteFilter;
import br.com.carloser7.asstecnica.domain.filter.TopClientesFilter;
import br.com.carloser7.asstecnica.domain.repository.projection.ClienteResumoProjection;

import java.util.List;

public interface ClienteResitoryQueries {

    List<ClienteResumoProjection> pesquisar(ClienteFilter clienteFilter);
    TopClientesEstatistica topClientes(TopClientesFilter filter);
}
