package br.com.carloser7.asstecnica.api.exceptionhandler;

public enum ErroType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado");

    private final String title;
    private final String uri;

    ErroType(String path, String title) {
        this.title = title;
        this.uri = "https://asstecnica.com.br" + path;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }
}
