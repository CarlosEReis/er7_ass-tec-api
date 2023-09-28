package br.com.carloser7.asstecnica.domain.model;

/**
 * Descreve os status do itens do chamado Técnico
 * <br>
 * <br> PENDENTE
 * <br> AVALIANDO
 * <br> AVALIADO
 * <br>
 */
public enum StatusItemChamadoTecnico {
    
    PENDENTE("Pendente"),
    AVALIANDO("Avaliando"),
    AVALIADO("Avaliado");

    private String descricao;

    StatusItemChamadoTecnico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
