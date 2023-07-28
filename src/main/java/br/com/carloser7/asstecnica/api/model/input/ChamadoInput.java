package br.com.carloser7.asstecnica.api.model.input;

import java.util.List;

public class ChamadoInput {

    private ClienteIdInput cliente;
    private List<ItemChamadoInput> itens;

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

    @Override
    public String toString() {
        return "ChamadoInput [cliente=" + cliente + ", itens=" + itens + "]";
    }

    
}
