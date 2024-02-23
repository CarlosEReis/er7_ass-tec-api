package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.repository.projection.ClienteView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, ClienteResitoryQueries {

    Page<ClienteView> findAllBy(Pageable pageable);
    Page<ClienteView> findByNomeContainingOrDocumentoContaining(String nome, String documento, Pageable pageable);
}
