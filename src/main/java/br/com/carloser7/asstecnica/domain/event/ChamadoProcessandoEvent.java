package br.com.carloser7.asstecnica.domain.event;

import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import org.springframework.context.ApplicationEvent;

public class ChamadoProcessandoEvent extends ApplicationEvent {

    private ChamadoTecnico chamado;

    public ChamadoProcessandoEvent(Object source, ChamadoTecnico chamado) {
        super(source);
        this.chamado = chamado;
    }

    public ChamadoTecnico getChamado() {
        return chamado;
    }
}
