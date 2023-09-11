package br.com.carloser7.asstecnica.core.validation;

import br.com.carloser7.asstecnica.domain.model.TipoCliente;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class DocumentoTipoPessoaValidator implements ConstraintValidator<DocumentoTipoPessoa, Object> {

    private String tipoPessoa;
    private String documento;

    @Override
    public void initialize(DocumentoTipoPessoa constraint) {
        this.tipoPessoa = constraint.tipoPessoa();
        this.documento = constraint.documento();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;
        try {
            TipoCliente tipoPessoa = (TipoCliente) BeanUtils.getPropertyDescriptor(object.getClass(), this.tipoPessoa).getReadMethod().invoke(object);
            String documento = (String) BeanUtils.getPropertyDescriptor(object.getClass(), this.documento).getReadMethod().invoke(object);

            if (Objects.isNull(tipoPessoa) || Objects.isNull(documento))
                return false;

            if (tipoPessoa.equals(TipoCliente.JURIDICA)) valido = validaCNPJ(documento);

            if (tipoPessoa.equals(TipoCliente.FISICA)) valido = validaCPF(documento);

        } catch (Exception e) {
            throw new ValidationException(e);
        }
        return valido;
    }

    private boolean validaCPF(String cpf) {
        String[] cpfPatterns = {
                "([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})|([0-9]{11})",

                "^(?:(?!000\\.?000\\.?000-?00).)*$",
        
                "^(?:(?!111\\.?111\\.?111-?11).)*$",
        
                "^(?:(?!222\\.?222\\.?222-?22).)*$",
        
                "^(?:(?!333\\.?333\\.?333-?33).)*$",
        
                "^(?:(?!444\\.?444\\.?444-?44).)*$",
        
                "^(?:(?!555\\.?555\\.?555-?55).)*$",
        
                "^(?:(?!666\\.?666\\.?666-?66).)*$",
        
                "^(?:(?!777\\.?777\\.?777-?77).)*$",
        
                "^(?:(?!888\\.?888\\.?888-?88).)*$",
        
                "^(?:(?!999\\.?999\\.?999-?99).)*$"
        };

        for (String pattern : cpfPatterns) {
            if (!cpf.matches(pattern)) {
                return false;
            }
        }
        return true;
    }

    private boolean validaCNPJ(String cnpj) {
        var cnpjPattern = "([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})";
        if (!cnpj.matches(cnpjPattern)){
            return false;
        }
        return true;
    }

}
