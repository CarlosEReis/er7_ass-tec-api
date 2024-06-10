package br.com.carloser7.asstecnica.domain.filter;

import java.time.LocalDate;

public record TopClientesFilter(
    Integer top,
    LocalDate dataInicial,
    LocalDate dataFinal
) { }
