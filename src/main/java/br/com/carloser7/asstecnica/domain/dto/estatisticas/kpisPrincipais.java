package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;

public record kpisPrincipais (
        String data,
        StatusChamadoTecnico status,
        Long quantidade
) { }