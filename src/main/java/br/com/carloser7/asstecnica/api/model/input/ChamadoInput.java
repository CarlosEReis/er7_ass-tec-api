package br.com.carloser7.asstecnica.api.model.input;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ChamadoInput {

    @NotNull private ClienteIdInput cliente;
    @NotNull private List<ItemChamadoInput> itens;
    @NotNull private List<ChamadoContatoInput> contatos;

    public ClienteIdInput getCliente() {
        return cliente;
    }

    public void setCliente(ClienteIdInput cliente) {
        this.cliente = cliente;
    }

    public List<ItemChamadoInput> getItens() {
        return itens;
    }

    public void setItens(List<ItemChamadoInput> itens) {
        this.itens = itens;
    }

    public List<ChamadoContatoInput> getContatos() {
        return contatos;
    }

    public void setContatos(List<ChamadoContatoInput> contatos) {
        this.contatos = contatos;
    }

}
