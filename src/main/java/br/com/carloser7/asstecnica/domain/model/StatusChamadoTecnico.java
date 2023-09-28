package br.com.carloser7.asstecnica.domain.model;

/**
 * Descreve os status de um Chamado Técnico
 * <br>
 * <br> FILA
 * <br> PROCESSANDO
 * <br> FINALIZADO
 * <br>
 */
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
