package br.com.carloser7.asstecnica.domain.repository.projection;

import java.time.LocalDateTime;

public interface ChamadoTecnicoView {
    
    Integer getId();
    LocalDateTime getDataCriacao();
    String getStatus();
    ClienteView getCliente();

    interface ClienteView {
        Integer getId();
        String getNome();
        String getDocumento();
    }

}
