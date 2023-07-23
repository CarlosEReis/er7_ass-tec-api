package br.com.carloser7.asstecnica.projection;

import java.time.LocalDateTime;

public interface ChamadoTecnicoProjection {
    
    Integer getId();
    LocalDateTime getDataCriacao();
    String getStatus();

    Integer getClienteId();
    String getClienteNome();
    String getClienteDocumento();
}
