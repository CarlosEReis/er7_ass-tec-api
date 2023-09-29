package br.com.carloser7.asstecnica.domain.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Integer id;

    public RecursoCriadoEvent(Object source) {
        super(source);
    }

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Integer id) {
        super(source);
        this.id = id;
        this.response = response;
    }

    public Integer getId() {
        return id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
