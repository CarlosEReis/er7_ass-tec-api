package br.com.carloser7.asstecnica.api.controller.sse;

import br.com.carloser7.asstecnica.domain.event.ChamadoConcluidoEvent;
import br.com.carloser7.asstecnica.domain.event.ChamadoCriadoEvent;
import br.com.carloser7.asstecnica.domain.event.ChamadoEvent;
import br.com.carloser7.asstecnica.domain.event.ChamadoProcessandoEvent;
import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AlertService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter watchAlerts() {
        // TODO: Ajustar timeout para o mesmo tempo de vida do token JWT
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        if (emitters.isEmpty()){
            SseEmitter.SseEventBuilder builder = SseEmitter.event()
                .name("")
                .data("Eventos aberto");
            try {
                emitter.send(builder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        emitters.add(emitter);
        return emitter;
    }

    public void addNovoAlerta(SseChamadoDto chamadoDto) {
        emitters.forEach((emitter) -> {
            log.info("Eviando novo alerta " + chamadoDto.id());
            try {
                SseEmitter.SseEventBuilder builder = SseEmitter.event()
                    .id(chamadoDto.id().toString())
                    .name(chamadoDto.eventType().toString())
                    .data(chamadoDto);
                emitter.send(builder);
            } catch (ClientAbortException e) {
                log.warn("Cliente fechou a conexão");
                emitters.remove(emitter);
            } catch (Exception e) {
                log.error("Erro ao enviar uma mensagem via SSE " + chamadoDto.id(), e);
                emitters.remove(emitter);
            }
        });
    }

    @EventListener(
        classes = { ChamadoCriadoEvent.class, ChamadoProcessandoEvent.class, ChamadoConcluidoEvent.class })
    public void chamadoCriadoListener(ChamadoEvent chamadoEvent) throws IOException {
        var chamado = chamadoEvent.getChamado();
        var chamadoDto = new SseChamadoDto(
            getEventType(chamado.getStatus()),
            chamado.getId(),
                new SseClienteDto(
                    chamado.getCliente().getId(),
                    chamado.getCliente().getNome()
                ));
        addNovoAlerta(chamadoDto);
    }

    private EventType getEventType(StatusChamadoTecnico status) {
        return
            switch (status) {
                case FILA ->  EventType.CHAMADO_CRIADO;
                case PROCESSANDO -> EventType.CHAMADO_PROCESSANDO;
                case FINALIZADO -> EventType.CHAMADO_FINALIZADO;
                default ->
                        throw new IllegalArgumentException("Trate o tipo de evento");
            };
    }
}

