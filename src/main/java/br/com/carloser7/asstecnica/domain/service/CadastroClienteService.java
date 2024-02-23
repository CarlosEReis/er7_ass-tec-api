package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.domain.event.RecursoCriadoEvent;
import br.com.carloser7.asstecnica.domain.exception.ClienteNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.exception.EntidadeEmUsoException;
import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.repository.ClienteRepository;
import br.com.carloser7.asstecnica.domain.repository.projection.ClienteView;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class CadastroClienteService {

    private static final String MSG_CLIENTE_EM_USO =
        "Cliente de código %s, não pode ser removido, pois está em uso.";

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ApplicationEventPublisher publisher;

    public Cliente adicionar(Cliente cliente, HttpServletResponse response) {
        cliente.getContatos().forEach(contato -> contato.setCliente(cliente));
        var clienteSalvo = this.clienteRepository.save(cliente);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));
        return clienteSalvo;
    }

    public Cliente buscar(Integer clienteID) {
        return clienteRepository
            .findById(clienteID)
            .orElseThrow(
                () -> new ClienteNaoEncontradoException(clienteID));
    }

    public Page<ClienteView> pesquisar(String nome, String documento, Pageable pageable) {
        if (StringUtils.hasText(nome) || StringUtils.hasText(documento)) {
            return this.clienteRepository.findByNomeContainingOrDocumentoContaining(nome, documento, pageable);
        }
        return this.clienteRepository.findAllBy(pageable);
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
