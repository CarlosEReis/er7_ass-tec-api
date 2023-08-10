package br.com.carloser7.asstecnica.api.model.input;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class ChamadoInput {

    @NotNull private ClienteIdInput cliente;
    @NotNull private List<ItemChamadoInput> itens;
    @NotNull private List<ContatoInput> contatos;

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

    public List<ContatoInput> getContatos() {
        return contatos;
    }

    public void setContatos(List<ContatoInput> contatos) {
        this.contatos = contatos;
    }
    
}
