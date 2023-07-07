package br.com.carloser7.asstecnica.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record Endereco (
    
    @NotBlank String cep,
    String logradouro,
    String numero,
    String complemento,
    @NotBlank String bairro,
    @NotBlank String cidade,
    @NotBlank String estado)
{}
