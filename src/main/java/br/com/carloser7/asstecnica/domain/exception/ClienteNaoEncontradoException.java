package br.com.carloser7.asstecnica.domain.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public static final String S_NÃO_EXISTE = "Cliente com o id %s não existe";

    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ClienteNaoEncontradoException(Integer clienteId) {
        this(String.format(S_NÃO_EXISTE, clienteId));
    }
}
