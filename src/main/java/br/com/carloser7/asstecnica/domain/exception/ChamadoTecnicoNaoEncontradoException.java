package br.com.carloser7.asstecnica.domain.exception;

public class ChamadoTecnicoNaoEncontradoException extends RecursoNaoEncontradoException{

    public static final String CHAMADO_INEXISTENTE = "Chamado Tecnico com o id %s não existe";

    public ChamadoTecnicoNaoEncontradoException(Integer chamadoId) {
        super(String.format(CHAMADO_INEXISTENTE, chamadoId));
    }
}
