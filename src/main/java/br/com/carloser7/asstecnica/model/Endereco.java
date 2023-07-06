package br.com.carloser7.asstecnica.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Endereco (
    
    String cep,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade)

{}
