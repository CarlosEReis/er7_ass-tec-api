package br.com.carloser7.asstecnica.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("ITEM")
public class StatusItemChamadoObject extends Status {

    @Enumerated(EnumType.STRING)
    private StatusItemChamadoTecnico status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_item_chamado")
    private ItemChamadoTecnico itemChamado;

    public StatusItemChamadoObject(){}

    public StatusItemChamadoObject(LocalDateTime data, String usuario, StatusItemChamadoTecnico status, ItemChamadoTecnico itemChamado) {
        super(data, usuario);
        this.status = status;
        this.itemChamado = itemChamado;
    }

    public StatusItemChamadoTecnico getStatus() {
        return status;
    }

    public void setStatus(StatusItemChamadoTecnico status) {
        this.status = status;
    }

    public ItemChamadoTecnico getItemChamado() {
        return itemChamado;
    }

    public void setItemChamado(ItemChamadoTecnico itemChamado) {
        this.itemChamado = itemChamado;
    }
}
