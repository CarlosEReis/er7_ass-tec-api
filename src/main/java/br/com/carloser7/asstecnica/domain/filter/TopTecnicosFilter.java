package br.com.carloser7.asstecnica.domain.filter;

import java.time.OffsetDateTime;
import java.time.Year;
import java.time.ZoneOffset;

public record TopTecnicosFilter(
        Integer top,
        DataFilter data
) {
    public TopTecnicosFilter {
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
