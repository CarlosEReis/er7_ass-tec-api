package br.com.carloser7.asstecnica.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Entity
@Table(name = "chamado")
public class ChamadoTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dtcriacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusChamadoTecnico status;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="chamadoTecnico", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<ItemChamadoTecnico> itens = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "chamado_contato",
        joinColumns = @JoinColumn(name = "id_chamado"),
        inverseJoinColumns = @JoinColumn(name = "id_contato"))
    private List<Contato> contatos = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chamadoTecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusChamadoObject> statusList = new Stack<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusChamadoTecnico getStatus() {
        return status;
    }

    public void setStatus(StatusChamadoTecnico status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemChamadoTecnico> getItens() {
        return itens;
    }

    public void setItens(List<ItemChamadoTecnico> itens) {
        this.itens = itens;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public List<StatusChamadoObject> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusChamadoObject> statusList) {
        this.statusList = statusList;
    }

    public void enviarParaFila(String usuario) {
        setStatus(usuario, StatusChamadoTecnico.FILA);
    }

    public void enviarParaProcessamento(String usuario) {
        setStatus(usuario, StatusChamadoTecnico.PROCESSANDO);
    }

    public void finalizar(String usuario) {
        setStatus(usuario, StatusChamadoTecnico.FINALIZADO);
    }

    @Transient
    public StatusChamadoTecnico getUltimoStatus() {
        if (this.statusList.isEmpty()) {
            throw new IllegalStateException("Lista de status vazia.");
        }
        var statusSize = this.statusList.size();
        return this.statusList.get(statusSize - 1).getStatus();
    }

    public boolean todosItemAvaliados() {
        var avaliados = true;
        for (ItemChamadoTecnico item : itens) {
            if (item.naoEstaAvaliado()) {
                avaliados = false;
                break;
            }
        }
        return avaliados;
    }

    public boolean estaNaFila() {
        return StatusChamadoTecnico.FILA.equals(getUltimoStatus());
    }

    public boolean estaEmProcessamento() {
        return StatusChamadoTecnico.PROCESSANDO.equals(getUltimoStatus());
    }

    public boolean estaFinalizado() {
        return StatusChamadoTecnico.FINALIZADO.equals(getUltimoStatus());
    }

    private void setStatus(String usuario, StatusChamadoTecnico status) {
        getStatusList().add(
            new StatusChamadoObject(
                usuario,
                status,
                this
            ));
        setStatus(status);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChamadoTecnico other = (ChamadoTecnico) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
