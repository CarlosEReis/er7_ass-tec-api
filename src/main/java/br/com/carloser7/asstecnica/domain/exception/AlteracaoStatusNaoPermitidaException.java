package br.com.carloser7.asstecnica.domain.exception;

public class AlteracaoStatusNaoPermitidaException extends OperacaoNaoPermitidaException {

    public AlteracaoStatusNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}
