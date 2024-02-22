package br.com.carloser7.asstecnica.domain.repository.projection;

import br.com.carloser7.asstecnica.domain.model.TipoCliente;

public record ClienteResumoProjection(

    Integer id,
    String nome,
    TipoCliente tipoPessoa,
    String documento,
    EnderesoResumoProjection endereco
) { }
