package br.com.carloser7.asstecnica.security;

import br.com.carloser7.asstecnica.domain.model.Usuario;
import br.com.carloser7.asstecnica.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AppUserDetailsSevice implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = this.usuarioRepository.findByEmail(email).orElseThrow(() ->
                new EmptyResultDataAccessException("Usuário com o e-mail" + email + " não foi encontrado", 1));
        return new UsuarioSistema(usuario, getPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        return usuario.getPermissoes()
            .stream()
            .map(p -> new SimpleGrantedAuthority(p.getNome()))
            .collect(Collectors.toSet());
    }
}