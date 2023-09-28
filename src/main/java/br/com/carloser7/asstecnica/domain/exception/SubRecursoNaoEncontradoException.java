package br.com.carloser7.asstecnica.domain.exception;

public class SubRecursoNaoEncontradoException extends RuntimeException {

    SubRecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
