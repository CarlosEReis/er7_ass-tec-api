package br.com.carloser7.asstecnica.domain.exception;

public class OperacaoNaoPermitidaException extends RuntimeException{

    OperacaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}
