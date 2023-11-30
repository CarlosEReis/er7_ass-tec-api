package br.com.carloser7.asstecnica.infra;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /*@EventListener
    public void teste(ApplicationReadyEvent event) {
        String template = "mail/aviso-lancamentos-vencidos";

        this.enviarEmail("carlos.er7@hotmail.com", List.of("carlos.er7@gmail.com"), "Teste", "Olá Mundo! <br>Este é um e-mail de teste.");
        System.out.println("Envio de e-mail terminado....");
    }*/

    public void enviarEmailComAnexo(String remetente, List<String> destinatarios, String assunto, String mensagem, String nomeAxexo, byte[] anexo) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
            helper.setSubject(assunto);
            helper.setText(mensagem, true);

            DataSource dataSource = new ByteArrayDataSource(anexo, "application/pdf");
            helper.addAttachment(nomeAxexo, dataSource);

            mailSender.send(mimeMessage);
            System.out.println("\n\nE-MAIL ENVIADO COM SUCESSO\n\n");
        } catch (MessagingException e) {
            throw new RuntimeException("Problemas com o envio de e-mail", e);
        }
    }

    public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
            helper.setSubject(assunto);
            helper.setText(mensagem, true);

            mailSender.send(mimeMessage);
            System.out.println("\n\nE-MAIL ENVIADO COM SUCESSO\n\n");
        } catch (MessagingException e) {
            throw new RuntimeException("Problemas com o envio de e-mail", e);
        }
    }

}
