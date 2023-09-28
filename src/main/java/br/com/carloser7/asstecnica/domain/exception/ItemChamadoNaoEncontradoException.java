package br.com.carloser7.asstecnica.domain.exception;

public class ItemChamadoNaoEncontradoException extends SubRecursoNaoEncontradoException{

    public static final String CHAMADO_INEXISTENTE = "SubRecurso 'ItemChamado' com o id %s não existe";

    public ItemChamadoNaoEncontradoException(Integer idItemChamado) {
        super(String.format(CHAMADO_INEXISTENTE, idItemChamado));
    }
}
