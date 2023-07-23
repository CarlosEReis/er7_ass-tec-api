package br.com.carloser7.asstecnica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.carloser7.asstecnica.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.projection.ChamadoTecnicoProjection;

@Repository
public interface ChamadoTecnicoRepository extends JpaRepository<ChamadoTecnico, Integer> {
    
    List<ChamadoTecnicoProjection> findAllProjectionBy();
    List<ChamadoTecnicoProjection> findByClienteNomeContaining(String nome);

}
