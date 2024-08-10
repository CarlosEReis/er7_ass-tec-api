package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;

public record KpisPrincipais2(
        StatusChamadoTecnico status,
        Long qtdeBase,
        Long qtdeConfronto,
        Long diferenca,
        double percentual
) {
}
