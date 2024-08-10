package br.com.carloser7.asstecnica.domain.dto.estatisticas;

public final class QtdeChamadoAbertosFechados {
    private final String data;
    private final String status;
    private final Long qtde;

    public QtdeChamadoAbertosFechados(String data, String status, Long qtde) {
        this.data = data;
        this.status = status;
        this.qtde = qtde;
    }

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public Long getQtde() {
        return qtde;
    }

    @Override
    public String toString() {
        return "QtdeChamadoAbertosFechados[" +
                "data=" + data + ", " +
                "status=" + status + ", " +
                "qtde=" + qtde + ']';
    }

}
