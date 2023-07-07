package br.com.carloser7.asstecnica.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;



    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<Erro> erros = criaListaDeErros(ex.getBindingResult());
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

    private List<Erro> criaListaDeErros(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            var msgUsuario = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            var msgDesenvolvedor = fieldError.toString();

            erros.add(new Erro(msgUsuario, msgDesenvolvedor));
        }
        return erros;
    }

    public static class Erro{
        
        private String msgUsuario;
        private String msgDesenvolvedor;

        public Erro(String msgUsuario, String msgDesenvolvedor){
            this.msgUsuario = msgUsuario;
            this.msgDesenvolvedor = msgDesenvolvedor;
        }

        public String getMsgUsuario() {
            return msgUsuario;
        }
        public void setMsgUsuario(String msgUsuario) {
            this.msgUsuario = msgUsuario;
        }
        public String getMsgDesenvolvedor() {
            return msgDesenvolvedor;
        }
        public void setMsgDesenvolvedor(String msgDesenvolvedor) {
            this.msgDesenvolvedor = msgDesenvolvedor;
        }

    } 

}
