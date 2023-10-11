package br.com.carloser7.asstecnica.api.model.input;

import br.com.carloser7.asstecnica.domain.model.StatusItemChamadoTecnico;

public class ConcluiAvaliacaoItemChamadoInput {

    private StatusItemChamadoTecnico status;
    private String posicaoTecnica;

    public StatusItemChamadoTecnico getStatus() {
        return status;
    }

    public void setStatus(StatusItemChamadoTecnico status) {
        this.status = status;
    }

    public String getPosicaoTecnica() {
        return posicaoTecnica;
    }

    public void setPosicaoTecnica(String posicaoTecnica) {
        this.posicaoTecnica = posicaoTecnica;
    }
}
