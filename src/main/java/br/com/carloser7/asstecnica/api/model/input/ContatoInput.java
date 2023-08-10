package br.com.carloser7.asstecnica.api.model.input;

public record ContatoInput (
    
    Integer id,
    String nome,
    String email,
    String telefone
    
) {}
