package br.com.carloser7.asstecnica.domain.listener;

import br.com.carloser7.asstecnica.domain.event.ChamadoConcluidoEvent;
import br.com.carloser7.asstecnica.domain.event.ChamadoCriadoEvent;
import br.com.carloser7.asstecnica.domain.event.ChamadoProcessandoEvent;
import br.com.carloser7.asstecnica.domain.model.Contato;
import br.com.carloser7.asstecnica.domain.service.CadastroChamadoTecnicoService;
import br.com.carloser7.asstecnica.infra.EmailService;
import br.com.carloser7.asstecnica.infra.service.FichaPdfChamadoService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// TODO: Refactor

@Component
public class ChamadoCriadoListener  {

    @Autowired private EmailService emailService;
    @Autowired private FichaPdfChamadoService fichaPdfChamadoService;
    @Autowired private CadastroChamadoTecnicoService chamadoTecnicoService;

    @Async
    @EventListener
    public void aoCriarNovoChamadoTecnico(ChamadoCriadoEvent event) throws JRException {

        var id = event.getChamado().getId();
        var chamado = this.chamadoTecnicoService.buscar(id);
        var cliente = chamado.getCliente().getNome();
        var contatos = getContatosEmail(chamado.getContatos());
        var mensagem = """
                Olá,<br>
                <br>
                Chamado de id %s para o cliente <strong> %s</strong>, foi criado com sucesso. <br>
  
                <br>
                O mesmo se encontra na <strong>fila de espera</strong>, e logo será avaliado por um de nossos analistas técnicos.
                <br>
                <br>
                <i>Atenciosamente,</i>
                <br>
                <span>Equipe</span>
                <br>
                <strong>ER7 SISTEMAS</strong>
                
                """
                .formatted(id, cliente);

        var assunto = "ER7 Sistemas \uD83D\uDFE0: Chamado %s ABERTO".formatted(id);
        var nomeAnexo = chamado.getId().toString().concat("_").concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddkkmm"))).concat(".pdf");
        String concat = LocalDateTime.now().toString();
        var fichaTecnica = fichaPdfChamadoService.relatorioFichaChamadoTecnico(chamado);

        this.emailService.enviarEmailComAnexo(
//                "carlos.er7@hotmail.com",
                "Carlos Reis <carlos.er7@gmail.com>",
                contatos,
                assunto,
                mensagem,
                nomeAnexo,
                fichaTecnica );
    }


    @Async
    @EventListener
    public void aoIniciarAvaliacaoChamado(ChamadoProcessandoEvent event) {

        var id = event.getChamado().getId();
        var contatos = getContatosEmail(event.getChamado().getContatos());
        var assunto = "ER7 Sistemas \uD83D\uDFE1: Chamado %s PROCESSANDO".formatted(id);
        var mensagem = """
                Olá,<br>
                <br>
                Estamos iniciando a avalização do chamado téncico (%s), e assim que finalizado, você será notificado,
                e receberá o laudo técnico.<br>
                
                <br>
                <br>
                <i>Atenciosamente,</i>
                <br>
                <span>Equipe</span>
                <br>
                <strong>ER7 SISTEMAS</strong>
                """
                .formatted(id);

        this.emailService.enviarEmail("carlos.er7@hotmail.com", contatos, assunto, mensagem);
    }

    @Async
    @EventListener
    public void aoConcluirAvaliacaoChamado(ChamadoConcluidoEvent event) throws JRException {

        var id = event.getChamado().getId();
        var cliente = event.getChamado().getCliente().getNome();
        var contatos = getContatosEmail(event.getChamado().getContatos());
        var assunto = "ER7 Sistemas \uD83D\uDFE2: Chamado %s AVALIACAO CONCLUÍDA ".formatted(id);
        var mensagem = """
                Olá,<br>
                <br>
                Concluímos a avaliação do chamado de id %s para o cliente <strong> %s</strong>. <br>
                <br>
                Abaixo está o anexo com o laudo técnico. Caso tenha alguma dúvida referente á posição técnica, não deixe
                 de entrar em contato atraveś dos nossos canais de atendimento.
                <br>
                <br>
                <i>Atenciosamente,</i>
                <br>
                <span>Equipe</span>
                <br>
                <strong>ER7 SISTEMAS</strong>
                """
                .formatted(id, cliente);


        var fichaTecnica = fichaPdfChamadoService.relatorioFichaChamadoTecnico(event.getChamado());
        var nomeAnexo = event.getChamado().getId().toString().concat("_").concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddkkmm"))).concat(".pdf");
        this.emailService.enviarEmailComAnexo("carlos.er7@hotmail.com", contatos, assunto, mensagem, nomeAnexo, fichaTecnica);
    }

    private static List<String> getContatosEmail(List<Contato> contatos) {
        return contatos
                .stream()
                .map(Contato::getEmail)
                .toList();
    }
}
