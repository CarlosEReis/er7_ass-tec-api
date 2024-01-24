package br.com.carloser7.asstecnica.api.exceptionhandler;

import br.com.carloser7.asstecnica.domain.exception.EntidadeEmUsoException;
import br.com.carloser7.asstecnica.domain.exception.NegocioException;
import br.com.carloser7.asstecnica.domain.exception.OperacaoNaoPermitidaException;
import br.com.carloser7.asstecnica.domain.exception.RecursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ NegocioException.class })
    public ResponseEntity<Object> handlerNegocioException(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiErro apiErro = new ApiErro(
                status.value(),
                ErroType.RECURSO_JA_EXISTENTE,
                ex.getMessage()
        );
        return handleExceptionInternal(ex, apiErro, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({ EntidadeEmUsoException.class })
    public ResponseEntity<Object> handlerEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiErro apiErro = new ApiErro(
            status.value(),
            ErroType.ENTIDADE_EM_USO,
            ex.getMessage()
        );
        return handleExceptionInternal(ex, apiErro, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({ RecursoNaoEncontradoException.class })
    public ResponseEntity<Object> handlerClienteNaoEncontradoException(RecursoNaoEncontradoException ex, WebRequest request) {
        ApiErro apiErro = new ApiErro(
                HttpStatus.NOT_FOUND.value(),
                ErroType.RECURSO_NAO_ENCONTRADO,
                ex.getMessage()
        );
        return handleExceptionInternal(ex, apiErro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ OperacaoNaoPermitidaException.class })
    public ResponseEntity<Object> handlerOperacaoNaoPermitidaException(OperacaoNaoPermitidaException ex, WebRequest request) {
        ApiErro apiErro = new ApiErro(
            HttpStatus.BAD_REQUEST.value(),
            ErroType.OPERACAO_NAO_PERMITIDA,
            ex.getMessage()
        );
        return handleExceptionInternal(ex, apiErro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ApiErro.Field> fieldErrors = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map((objectError) -> {
                var mensagem = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                String name = objectError.getObjectName();
                if (objectError instanceof FieldError) {
                    name = ((FieldError) objectError).getField();
                }
                return new ApiErro.Field(name, mensagem);
            })
            .toList();

        ApiErro apiErro = new ApiErro(
            HttpStatus.BAD_REQUEST.value(),
            ErroType.DADOS_INVALIDOS,
            "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
            fieldErrors
        );
        return handleExceptionInternal(ex, apiErro, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    /*
    private List<ApiErro> criaListaDeErros(BindingResult bindingResult) {
        List<ApiErro> erros = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            var msgUsuario = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            var msgDesenvolvedor = fieldError.toString();
            erros.add(new ApiErro(msgUsuario, msgDesenvolvedor));
        }
        return erros;
    }*/
}
