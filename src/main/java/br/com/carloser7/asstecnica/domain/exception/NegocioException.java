package br.com.carloser7.asstecnica.domain.exception;

public class NegocioException extends RuntimeException{

    NegocioException(String mensagem) {
        super(mensagem);
    }

    NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
