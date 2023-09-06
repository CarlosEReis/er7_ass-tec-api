package br.com.carloser7.asstecnica.api.exceptionhandler;

public class ApiErro {

    private String msgUsuario;
    private String msgDesenvolvedor;

    public ApiErro (String msgUsuario, String msgDesenvolvedor){
        this.msgUsuario = msgUsuario;
        this.msgDesenvolvedor = msgDesenvolvedor;
    }

    public String getMsgUsuario() {
        return msgUsuario;
    }

    public String getMsgDesenvolvedor() {
        return msgDesenvolvedor;
    }

}
