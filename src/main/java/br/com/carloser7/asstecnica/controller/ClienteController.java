package br.com.carloser7.asstecnica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carloser7.asstecnica.model.Cliente;
import br.com.carloser7.asstecnica.repository.ClienteRepository;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> listar() {
        return this.clienteRepository.findAll();
    }

}
