package br.com.carloser7.asstecnica.domain.listener;

import br.com.carloser7.asstecnica.domain.event.ChamadoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChamadoCriadoListener implements ApplicationListener<ChamadoCriadoEvent> {
    @Override
    public void onApplicationEvent(ChamadoCriadoEvent event) {

        var id = event.getChamado().getId();
        var cliente = event.getChamado().getCliente().getNome();

        System.out.print("\n\n\n<<<<<<<<<< NOVO CLIENTE ADICIONADO >>>>>>>>>>");
        System.out.println("id: " + id);
        System.out.println("Cliente: " + cliente);
    }
}
