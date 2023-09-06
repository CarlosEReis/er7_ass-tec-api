package br.com.carloser7.asstecnica.domain.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
