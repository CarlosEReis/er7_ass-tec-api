package br.com.carloser7.asstecnica.api.disassembler;

import br.com.carloser7.asstecnica.api.model.input.ContatoInput;
import br.com.carloser7.asstecnica.domain.model.Contato;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ContatoInputDisassembler {

    private ModelMapper modelMapper;

    public ContatoInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Contato toDomainObject(ContatoInput contatoInput) {
        return modelMapper.map(contatoInput, Contato.class);
    }
}
