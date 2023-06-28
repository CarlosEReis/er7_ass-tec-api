package br.com.carloser7.asstecnica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carloser7.asstecnica.model.Usuario;
import br.com.carloser7.asstecnica.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;    

    @PostMapping
    private Usuario cria(@RequestBody Usuario usuario) {
        Usuario usuariosSaved = this.usuarioRepository.save(usuario);
        return usuariosSaved;
    }

    @GetMapping
    public List<Usuario> lista() {
        return this.usuarioRepository.findAll();
    }
}
