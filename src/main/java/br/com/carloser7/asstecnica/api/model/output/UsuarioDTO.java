package br.com.carloser7.asstecnica.api.model.output;

import br.com.carloser7.asstecnica.domain.model.Permissao;

import java.util.List;

public record UsuarioDTO(
        Integer id,
        String nome,
        String email,
        List<Permissao> permissoes) { }
