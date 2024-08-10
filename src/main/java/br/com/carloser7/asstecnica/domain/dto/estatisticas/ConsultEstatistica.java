package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConsultEstatistica {

    private final LocalDate dataInicial;
    private final LocalDate dataFinal;
    private final DateFitlterType tipoFiltro;
    private final LocalDateTime dataConsulta;

    public ConsultEstatistica(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro,
                              LocalDateTime dataConsulta) {
        this.dataInicial = dataBase;
        this.dataFinal = dataConfronto;
        this.tipoFiltro = tipoFiltro;
        this.dataConsulta = dataConsulta;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public DateFitlterType getTipoFiltro() {
        return tipoFiltro;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

}