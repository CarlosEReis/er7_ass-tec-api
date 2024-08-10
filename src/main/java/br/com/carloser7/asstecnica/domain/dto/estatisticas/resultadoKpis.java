package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;

public class resultadoKpis {

    private StatusChamadoTecnico status;
    private Long anoBase;
    private Long anoConfronto;
    private float percent = 0.0f;

    public resultadoKpis(StatusChamadoTecnico status, Long anoBase, Long anoConfronto) {
        this.status = status;
        this.anoBase = anoBase;
        this.anoConfronto = anoConfronto;
        calculaPercent();
    }

    public StatusChamadoTecnico getStatus() {
        return status;
    }

    public Long getAnoBase() {
        return anoBase;
    }

    public Long getAnoConfronto() {
        return anoConfronto;
    }

    public Long getDiferenca() {
        return anoBase - anoConfronto;
    }

    public float getPercent() {
        return percent;
    }

    public void calculaPercent() {
        if (anoBase != 0)
            this.percent = ((float) (anoBase - anoConfronto) / anoBase) * 100;
        else
            this.percent = 0;
    }
}
