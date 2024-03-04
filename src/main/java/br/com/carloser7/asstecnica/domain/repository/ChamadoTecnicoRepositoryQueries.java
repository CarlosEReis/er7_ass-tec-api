package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.DateFitlterType;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.QtdeChamadoAbertosFechados;

import java.time.LocalDate;
import java.util.List;

public interface ChamadoTecnicoRepositoryQueries {

    List<QtdeChamadoAbertosFechados> chamadosAbertosFechados(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType tipo);
}
