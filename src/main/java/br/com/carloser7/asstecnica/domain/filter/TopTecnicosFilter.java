package br.com.carloser7.asstecnica.domain.filter;

import java.time.LocalDate;

public record TopTecnicosFilter(
        Integer top,
        LocalDate dataInicial,
        LocalDate dataFinal
) { }

