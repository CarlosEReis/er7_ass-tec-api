package br.com.carloser7.asstecnica.domain.exception;

public class ClienteNaoEncontradoException extends RecursoNaoEncontradoException {

    public static final String CLIENTE_INEXISTENTE = "Cliente com o id %s não existe";

    public ClienteNaoEncontradoException(Integer clienteId) {
        super(String.format(CLIENTE_INEXISTENTE, clienteId));
    }
}
