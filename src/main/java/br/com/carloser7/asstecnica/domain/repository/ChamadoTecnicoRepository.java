package br.com.carloser7.asstecnica.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.repository.projection.ChamadoTecnicoView;

@Repository
public interface ChamadoTecnicoRepository extends JpaRepository<ChamadoTecnico, Integer> {

    Page<ChamadoTecnicoView> findChamadoTecnicoBy(Pageable pageable);
    Page<ChamadoTecnicoView> findByClienteNomeContaining(String nome, Pageable pageable);

}
