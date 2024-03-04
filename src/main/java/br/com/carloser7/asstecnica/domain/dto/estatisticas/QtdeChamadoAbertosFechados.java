package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import java.time.LocalDate;

public record QtdeChamadoAbertosFechados(
        LocalDate data,
        Long abertos,
        Long fechados) {
}
