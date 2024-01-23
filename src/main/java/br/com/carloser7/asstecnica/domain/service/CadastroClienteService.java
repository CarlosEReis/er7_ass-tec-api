package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.domain.exception.ClienteNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.exception.EntidadeEmUsoException;
import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CadastroClienteService {

    private static final String MSG_CLIENTE_EM_USO =
        "Cliente de código %s, não pode ser removido, pois está em uso.";

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscar(Integer clienteID) {
        return clienteRepository
            .findById(clienteID)
            .orElseThrow(
                () -> new ClienteNaoEncontradoException(clienteID));
    }

    public void remover(Integer clienteID) {
        Assert.notNull(clienteID, "O ID do cliente não pode ser nulo");
        try {
            buscar(clienteID);
            clienteRepository.deleteById(clienteID);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CLIENTE_EM_USO, clienteID)
            );
        }
    }
}
