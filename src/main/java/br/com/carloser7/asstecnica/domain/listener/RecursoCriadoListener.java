package br.com.carloser7.asstecnica.domain.listener;

import br.com.carloser7.asstecnica.domain.event.RecursoCriadoEvent;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent event) {
        var id = event.getId();
        var response = event.getResponse();
        adicionaHeaderLocation(response, id);
    }

    private void adicionaHeaderLocation(HttpServletResponse response, Integer id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
