package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, UsuarioRepositoryQueries {

    Optional<Usuario> findByEmail(String email);
}
