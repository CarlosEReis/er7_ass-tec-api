package br.com.carloser7.asstecnica.api.exceptionhandler;

import br.com.carloser7.asstecnica.domain.exception.RecursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ RecursoNaoEncontradoException.class })
    public ResponseEntity<Object> handlerClienteNaoEncontradoException(RecursoNaoEncontradoException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        List<ApiErro> erros = List.of(new ApiErro(ex.getMessage(), ex.toString()));
        return super.handleExceptionInternal(ex, erros, headers, statusCode, request);
    }

    private List<ApiErro> criaListaDeErros(BindingResult bindingResult) {
        List<ApiErro> erros = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            var msgUsuario = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            var msgDesenvolvedor = fieldError.toString();
            erros.add(new ApiErro(msgUsuario, msgDesenvolvedor));
        }
        return erros;
    }
}
