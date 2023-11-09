package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;

import java.time.LocalDate;

public interface StatusChamadoMes {
    Integer getMes();
    StatusChamadoTecnico getStatus();
    Integer getQuantidade();
}
