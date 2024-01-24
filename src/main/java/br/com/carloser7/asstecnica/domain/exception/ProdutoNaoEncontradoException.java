package br.com.carloser7.asstecnica.domain.exception;

public class ProdutoNaoEncontradoException extends RecursoNaoEncontradoException {

    public static final String PRODUTO_INEXISTENTE = "Produto com o id %s não existe";

    public ProdutoNaoEncontradoException(String contatoId) {
        super(String.format(PRODUTO_INEXISTENTE, contatoId));
    }

}
