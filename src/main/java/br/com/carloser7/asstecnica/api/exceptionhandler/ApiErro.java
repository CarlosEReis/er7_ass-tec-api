package br.com.carloser7.asstecnica.api.exceptionhandler;

public class ApiErro {

    private Integer status;
    private String type;
    private String title;
    private String detail;


    /*@Deprecated private String msgUsuario;
    @Deprecated private String msgDesenvolvedor;

    public ApiErro (String msgUsuario, String msgDesenvolvedor){
        this.msgUsuario = msgUsuario;
        this.msgDesenvolvedor = msgDesenvolvedor;
    }*/

    public ApiErro(Integer status, ErroType erroType, String detail ) {
        this.status = status;
        this.type = erroType.getUri();
        this.title = erroType.getTitle();
        this.detail = detail;
    }

    public Integer getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return "ApiErro{" +
                "status=" + status +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
