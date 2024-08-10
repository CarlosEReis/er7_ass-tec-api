package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ConsultaEstatistica(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro,
                                  LocalDateTime dataConsulta, List<resultadoKpis> kpis) {

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