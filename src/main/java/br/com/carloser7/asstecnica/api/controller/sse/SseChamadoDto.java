package br.com.carloser7.asstecnica.api.controller.sse;

public record SseChamadoDto (
    EventType eventType,
    Integer id,
    SseClienteDto cliente) {
}

record SseClienteDto (
    Integer id,
    String nome){
};