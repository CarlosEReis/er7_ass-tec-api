package br.com.carloser7.asstecnica.domain.exception;

public class ContatoNaoEncontradoException extends RecursoNaoEncontradoException {

    public static final String CONTATO_INEXISTENTE = "Contato com o id %s não existe";
    public static final String CONTATO_INEXISTENTE_CLIENTE = "Contato de id %s não existe no cliente de id %s";

    public ContatoNaoEncontradoException(Integer contatoId) {
        super(String.format(CONTATO_INEXISTENTE, contatoId));
    }

    public ContatoNaoEncontradoException(Integer clienteId, Integer contatoId) {
        super(String.format(CONTATO_INEXISTENTE_CLIENTE, contatoId,clienteId));
    }

}
