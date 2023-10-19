package br.com.carloser7.asstecnica.domain.listener;

import br.com.carloser7.asstecnica.domain.event.ChamadoCriadoEvent;
import br.com.carloser7.asstecnica.domain.model.Contato;
import br.com.carloser7.asstecnica.domain.service.CadastroChamadoTecnicoService;
import br.com.carloser7.asstecnica.infra.EmailService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ChamadoCriadoListener  {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CadastroChamadoTecnicoService chamadoTecnicoService;

    @Async
    @EventListener
    public void aoCriarNovoChamadoTecnico(ChamadoCriadoEvent event) throws JRException {

        var id = event.getChamado().getId();
        var cliente = event.getChamado().getCliente().getNome();
        var contatos = event.getChamado()
                            .getContatos()
                                .stream()
                                .map(Contato::getEmail)
                                .toList();
        var mensagem = """
                Olá,<br>
                <br>
                Chamado de id %s foi criado para o cliente <strong> %s</strong>. <br>
                <br>
                O mesmo se encontra na <strong>fila de espera</strong>, e logo será avaliado por um de nossos analistas técnicos.
                """
                .formatted(id, cliente);

        var assunto = "ER7 Sistemas: Chamado %s aberto".formatted(id);
        var fichaTecnica = chamadoTecnicoService.relatorioFichaChamadoTecnico(event.getChamado());

        this.emailService.enviarEmailComAnexo(
                "carlos.er7@hotmail.com",
                contatos,
                assunto,
                mensagem,
                fichaTecnica );
    }

}
