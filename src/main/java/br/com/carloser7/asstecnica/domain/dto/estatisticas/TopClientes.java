package br.com.carloser7.asstecnica.domain.dto.estatisticas;

public record TopClientes(
    Integer id,
    String nome,
    Long quantidade
) { }
