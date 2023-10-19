package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.domain.exception.ContatoNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.model.Contato;
import br.com.carloser7.asstecnica.domain.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public Contato buscar(Integer id) {
        return contatoRepository
                .findById(id)
                .orElseThrow(
                        () -> new ContatoNaoEncontradoException(id));
    }

}
