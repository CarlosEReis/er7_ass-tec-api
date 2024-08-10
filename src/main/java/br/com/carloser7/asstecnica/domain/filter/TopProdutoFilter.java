package br.com.carloser7.asstecnica.domain.filter;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.DateFitlterType;

import java.time.LocalDate;

public record TopProdutoFilter(
        Integer top,
        LocalDate dataInicial,
        LocalDate dataFinal,
        DateFitlterType tipoFiltro
) {     }
