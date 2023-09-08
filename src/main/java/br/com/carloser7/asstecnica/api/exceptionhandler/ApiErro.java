package br.com.carloser7.asstecnica.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiErro(
        Integer status,
        String type,
        String title,
        String detail,
        List<Field> fieldErrors) {

    public ApiErro(Integer status, ErroType erroType, String detail) {
        this(status, erroType.getUri(), erroType.getTitle(), detail, new ArrayList<>());
    }

    public ApiErro(Integer status, ErroType erroType, String detail, List<Field> fieldErrors) {
        this(status, erroType, detail);
        this.fieldErrors.addAll(fieldErrors);
    }

    public record Field(String name, String message) {
    }
}


