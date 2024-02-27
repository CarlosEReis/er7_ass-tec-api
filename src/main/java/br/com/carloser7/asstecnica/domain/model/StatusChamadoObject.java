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
    @JoinColumn(name = "id_chamado")
    private ChamadoTecnico chamadoTecnico;

    public StatusChamadoObject() { }

    public StatusChamadoObject(String nomeUsuario, StatusChamadoTecnico status, ChamadoTecnico chamadoTecnico) {
        super(nomeUsuario);
        this.status = status;
        this.chamadoTecnico = chamadoTecnico;
    }

    public StatusChamadoTecnico getStatus() {
        return status;
    }

    public void setStatus(StatusChamadoTecnico status) {
        this.status = status;
    }

    public ChamadoTecnico getchamadoTecnico() {
        return chamadoTecnico;
    }

    public void setchamadoTecnico(ChamadoTecnico chamadoTecnico) {
        this.chamadoTecnico = chamadoTecnico;
    }
}
