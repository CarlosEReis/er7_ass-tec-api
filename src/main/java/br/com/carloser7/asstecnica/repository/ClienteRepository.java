package br.com.carloser7.asstecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.carloser7.asstecnica.domain.model.Cliente;
import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
 
    List<Cliente> findByNomeContaining(String nome);
}
