package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.*;

import java.time.LocalDate;
import java.util.List;

public interface ChamadoTecnicoRepositoryQueries {

    List<QtdeChamadoAbertosFechados> chamadosAbertosFechados(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType filter);

    ConsultaEstatisticaAbertosFechados chamadosAbertosFechadosNovo(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType tipo);

    List<kpisPrincipais> kpisPrincipais(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType filter);

    ConsultaEstatistica kpisPrincipalImpl(LocalDate data1, LocalDate data2, DateFitlterType tipoFiltro);

    ConsultaEstatistica kpisPrincipalImplDOIS(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro);

    ConsultaEstatisticaAbertosFechados kpisPrincipalImplAllDOIS(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro);
}
