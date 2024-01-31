package br.com.carloser7.asstecnica.domain.event;

import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;

public class ChamadoProcessandoEvent extends ChamadoEvent {

    public ChamadoProcessandoEvent(Object source, ChamadoTecnico chamado) {
        super(source, chamado);
    }
}
