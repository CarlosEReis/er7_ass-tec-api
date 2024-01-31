package br.com.carloser7.asstecnica.domain.event;

import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;

public class ChamadoCriadoEvent extends ChamadoEvent {

    public ChamadoCriadoEvent(Object source, ChamadoTecnico chamado) {
        super(source, chamado);
    }
}
