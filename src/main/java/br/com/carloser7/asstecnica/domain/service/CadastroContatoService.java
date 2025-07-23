package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.domain.exception.ClienteNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.exception.ContatoNaoEncontradoException;
import br.com.carloser7.asstecnica.domain.model.Contato;
import br.com.carloser7.asstecnica.domain.repository.ContatoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    public Contato buscar(Integer id) {
        return contatoRepository
            .findById(id)
            .orElseThrow(
                () -> new ContatoNaoEncontradoException(id));
    }

    @Transactional
    public Contato salvar(Integer clienteId, Contato contato) {
        var cliente = cadastroClienteService.buscar(clienteId);
        contato.setCliente(cliente);
        return contatoRepository.save(contato);
    }

    @Transactional
    public Contato atualiza(Integer cliendeId, Integer contatoId, Contato contato) {
        var contatoDb = buscar(contatoId);
        cadastroClienteService.buscar(cliendeId);
        if (!contatoDb.getCliente().getId().equals(cliendeId)) {
            throw new ContatoNaoEncontradoException(cliendeId, contatoId);
        }
        BeanUtils.copyProperties(contato,contatoDb, "id", "cliente");
        return contatoRepository.save(contatoDb);
    }

    public List<Contato> listarPorCliente(Integer clienteId) {
        boolean clienteExiste = cadastroClienteService.existePorId(clienteId);
        if (!clienteExiste)
            throw new ClienteNaoEncontradoException(clienteId);

        return contatoRepository.findByClienteId(clienteId);
    }
}
