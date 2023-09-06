package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.domain.exception.ClienteNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscar(Integer id) {
        return clienteRepository
            .findById(id)
            .orElseThrow(
                () -> new ClienteNaoEncontradoException(id));
    }
}
