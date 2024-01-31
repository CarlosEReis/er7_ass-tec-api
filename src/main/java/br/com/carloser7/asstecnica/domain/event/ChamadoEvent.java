package br.com.carloser7.asstecnica.domain.event;

import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import org.springframework.context.ApplicationEvent;

public abstract class ChamadoEvent extends ApplicationEvent {

    private final ChamadoTecnico chamado;

    public ChamadoEvent(Object source, ChamadoTecnico chamado) {
        super(source);
        this.chamado = chamado;
    }

    public ChamadoTecnico getChamado() {
        return chamado;
    }
}
