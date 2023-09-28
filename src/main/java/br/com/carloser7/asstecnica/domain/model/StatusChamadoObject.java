package br.com.carloser7.asstecnica.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CHAMADO")
public class StatusChamadoObject extends Status {

    @Enumerated(EnumType.STRING)
    private StatusChamadoTecnico status;

    @JsonIgnore
    @ManyToOne
    private ChamadoTecnico chamaoTecnico;

    public StatusChamadoTecnico getStatus() {
        return status;
    }

    public void setStatus(StatusChamadoTecnico status) {
        this.status = status;
    }

    public ChamadoTecnico getChamaoTecnico() {
        return chamaoTecnico;
    }

    public void setChamaoTecnico(ChamadoTecnico chamaoTecnico) {
        this.chamaoTecnico = chamaoTecnico;
    }
}
