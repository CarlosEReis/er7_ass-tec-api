package br.com.carloser7.asstecnica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carloser7.asstecnica.projection.ChamadoTecnicoProjection;
import br.com.carloser7.asstecnica.repository.ChamadoTecnicoRepository;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/chamados")
public class ChamadoTecnicoController {

    @Autowired
    private ChamadoTecnicoRepository chamadoTecnicoRepository;

    @GetMapping()
    public List<ChamadoTecnicoProjection> pesquisar(String nome) {
        if (StringUtils.hasText(nome)) {
            return this.chamadoTecnicoRepository.findByClienteNomeContaining(nome);
        }
        return this.chamadoTecnicoRepository.findAllProjectionBy();
    }
}
