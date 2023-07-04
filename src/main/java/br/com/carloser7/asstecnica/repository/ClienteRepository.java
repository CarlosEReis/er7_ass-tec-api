package br.com.carloser7.asstecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.carloser7.asstecnica.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
}
