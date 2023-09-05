package br.com.carloser7.asstecnica.domain.model;

public enum StatusChamadoTecnico {
    
    FILA("Na Fila"),
    PROCESSANDO("Processando"),
    FINALIZADO("Finalizado");

    private String descricao;

    StatusChamadoTecnico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
