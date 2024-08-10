package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ConsultaEstatisticaAbertosFechados(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro,
                                                 LocalDateTime dataConsulta, List<QtdeChamadoAbertosFechados> kpis) {

    @Override
    public String toString() {
        return "ConsultaEstatistica[" +
                "dataBase=" + dataBase + ", " +
                "dataConfronto=" + dataConfronto + ", " +
                "tipoFiltro=" + tipoFiltro + ", " +
                "dataConsulta=" + dataConsulta + ", " +
                "kpis=" + kpis + ']';
    }

}