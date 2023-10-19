package br.com.carloser7.asstecnica.domain.exception;

public class ContatoNaoEncontradoException extends RecursoNaoEncontradoException {

    public static final String CONTATO_INEXISTENTE = "Contato com o id %s não existe";

    public ContatoNaoEncontradoException(Integer contatoId) {
        super(String.format(CONTATO_INEXISTENTE, contatoId));
    }

}
