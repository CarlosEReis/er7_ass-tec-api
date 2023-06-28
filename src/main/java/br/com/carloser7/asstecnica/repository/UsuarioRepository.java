package br.com.carloser7.asstecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carloser7.asstecnica.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
