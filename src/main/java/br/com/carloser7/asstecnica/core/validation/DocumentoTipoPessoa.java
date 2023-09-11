package br.com.carloser7.asstecnica.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DocumentoTipoPessoaValidator.class})
public @interface DocumentoTipoPessoa {

    String message() default "O documento não é compatível com o tipo de pessoa.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String documento();
    String tipoPessoa();
}
