package br.com.carloser7.asstecnica.api.controller.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/events")
public class ApiSseController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/api-events")
    public SseEmitter teste() {
        return alertService.watchAlerts();
    }

}
