package br.com.carloser7.asstecnica.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.List;
import java.util.Stack;

@Entity
@Table(name = "ocorrencia_ct")
public class ItemChamadoTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String serial;
    private String descricao;

    @Column(name = "posi_tecn")
    private String posicaoTecnica;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto = new Produto();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_chamado")
    private ChamadoTecnico chamadoTecnico;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemChamado")
    private List<StatusItemChamadoObject> status = new Stack<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //TODO: Ajutar a geracao da ficha de chamado tecnico, para pegar o sku atraves do produto e remover o metodo abaixo
    @Transient
    public String getSku() {
        return this.getProduto().getSku();
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPosicaoTecnica() {
        return posicaoTecnica;
    }

    public void setPosicaoTecnica(String posicaoTecnica) {
        this.posicaoTecnica = posicaoTecnica;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ChamadoTecnico getChamadoTecnico() {
        return chamadoTecnico;
    }

    public void setChamadoTecnico(ChamadoTecnico chamadoTecnico) {
        this.chamadoTecnico = chamadoTecnico;
    }

    public List<StatusItemChamadoObject> getStatus() {
        return status;
    }

    public void setStatus(List<StatusItemChamadoObject> status) {
        this.status = status;
    }

    public void pendente(String usuario) {
        setStatus(usuario, StatusItemChamadoTecnico.PENDENTE);
    }

    public void avaliando(String usuario) {
        setStatus(usuario, StatusItemChamadoTecnico.AVALIANDO);
    }

    public void avaliado(String usuario) {
        setStatus(usuario, StatusItemChamadoTecnico.AVALIADO);
    }

    private void setStatus(String usuario, StatusItemChamadoTecnico status) {
        getStatus().add(
            new StatusItemChamadoObject(
                usuario,
                status,
                this
            ));
    }

    @Transient
    public StatusItemChamadoTecnico getUltimoStatus() {
        if (this.status.isEmpty()) {
            throw new IllegalStateException("Lista de status vazia.");
        }
        var statusSize = this.status.size();
        return this.getStatus().get(statusSize - 1).getStatus();
    }

    public boolean estaAvaliado() {
        return StatusItemChamadoTecnico.AVALIADO.equals(getUltimoStatus());
    }

    public boolean naoEstaAvaliado() {
        return !estaAvaliado();
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
        ItemChamadoTecnico other = (ItemChamadoTecnico) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
