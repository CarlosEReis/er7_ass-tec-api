package br.com.carloser7.asstecnica.domain.filter;

import java.time.OffsetDateTime;

public record DataFilter (
    OffsetDateTime dataInicial,
    OffsetDateTime dataFinal
) { }
