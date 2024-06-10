package br.com.carloser7.asstecnica.domain.filter;

import java.time.*;

public record TopProdutoFilter(
        Integer top,
        LocalDate dataInicial,
        LocalDate dataFinal
) {     }
