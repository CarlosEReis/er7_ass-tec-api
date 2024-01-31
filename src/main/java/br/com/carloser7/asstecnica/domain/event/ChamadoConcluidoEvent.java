package br.com.carloser7.asstecnica.domain.event;

import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;

public class ChamadoConcluidoEvent extends ChamadoEvent {

    public ChamadoConcluidoEvent(Object source, ChamadoTecnico chamado) {
        super(source, chamado);
    }
}
