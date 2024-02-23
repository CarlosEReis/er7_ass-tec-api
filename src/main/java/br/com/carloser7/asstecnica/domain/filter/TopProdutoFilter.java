package br.com.carloser7.asstecnica.domain.filter;

import java.time.*;

public record TopProdutoFilter(
        Integer top,
        DataFilter data
) {
    public TopProdutoFilter {
        if (top == null) top = 5;
        if (data == null) {
            data = new DataFilter(dataInicial(), OffsetDateTime.now(ZoneOffset.UTC));
        }
    }

    private OffsetDateTime dataInicial() {
        return OffsetDateTime.of(
            Year.now().getValue(),
            1,1, 0,
            0,0,0, ZoneOffset.UTC);
    }
}
