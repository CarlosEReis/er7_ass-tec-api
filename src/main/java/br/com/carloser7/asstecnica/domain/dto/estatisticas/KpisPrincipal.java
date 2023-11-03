package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;

public interface KpisPrincipal {

    StatusChamadoTecnico getStatus();
    Integer getQuantidade();

}
