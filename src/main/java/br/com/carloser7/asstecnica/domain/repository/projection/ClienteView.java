package br.com.carloser7.asstecnica.domain.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ClienteView {

    Integer getId();
    String getNome();
    String getTipoPessoa();
    String getDocumento();
    EnderecoProjection getEndereco();

    interface EnderecoProjection{
        @Value("#{target.cidade + ' - ' + target.estado}")
        String getLocalidade();
    }
}
