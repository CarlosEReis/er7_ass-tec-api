package br.com.carloser7.asstecnica.domain.repository.projection;

public record ProdutoResumoProjection(
        Integer id,
        String sku,
        String nome
) { }
