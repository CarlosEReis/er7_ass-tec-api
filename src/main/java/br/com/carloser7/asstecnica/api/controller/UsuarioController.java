package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.api.model.output.UsuarioDTO;
import br.com.carloser7.asstecnica.domain.model.Permissao;
import br.com.carloser7.asstecnica.domain.model.Usuario;
import br.com.carloser7.asstecnica.domain.repository.PermissaoRepository;
import br.com.carloser7.asstecnica.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UsuarioDTO adicionar(@RequestBody Usuario usuario) {
        usuario.
            setSenha(this.passwordEncoder.encode(usuario.getSenha()));
        return toUsuarioDTO(this.usuarioRepository.save(usuario));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UsuarioDTO> listar() {
        return this.toCollectionUsuarioDTO(this.usuarioRepository.findAll());
    }

    @GetMapping("/permissoes")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Permissao> listarPermissoes() {
        return this.permissaoRepository.findAll();
    }

    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPermissoes());
    }

    private List<UsuarioDTO> toCollectionUsuarioDTO(List<Usuario> usuarios) {
        return usuarios
            .stream()
            .map(this::toUsuarioDTO)
            .collect(Collectors.toList());
    }
}
