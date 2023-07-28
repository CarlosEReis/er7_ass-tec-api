package br.com.carloser7.asstecnica.api.model.input;

public class ClienteIdInput {
    
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClienteIdInput [id=" + id + "]";
    }

}
