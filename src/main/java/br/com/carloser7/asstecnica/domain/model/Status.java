package br.com.carloser7.asstecnica.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Status_Type")
public abstract class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataStatus;
    private String nomeUsuario;

    public Status() {}

    public Status(LocalDateTime dataStatus, String nomeUsuario) {
        this.dataStatus = dataStatus;
        this.nomeUsuario = nomeUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(LocalDateTime dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**public ChamadoTecnico getChamaoTecnico() {
        return chamaoTecnico;
    }

    public void setChamaoTecnico(ChamadoTecnico chamaoTecnico) {
        this.chamaoTecnico = chamaoTecnico;
    }**/

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Status status = (Status) object;
        return Objects.equals(id, status.id) && Objects.equals(dataStatus, status.dataStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataStatus);
    }
}
