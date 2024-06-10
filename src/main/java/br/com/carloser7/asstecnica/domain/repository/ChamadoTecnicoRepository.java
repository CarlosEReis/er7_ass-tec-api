package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.KpisPrincipal;
import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.repository.projection.ChamadoTecnicoView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ChamadoTecnicoRepository extends JpaRepository<ChamadoTecnico, Integer>, ChamadoTecnicoRepositoryQueries {

    Page<ChamadoTecnicoView> findChamadoTecnicoBy(Pageable pageable);
    Page<ChamadoTecnicoView> findByClienteNomeContaining(String nome, Pageable pageable);

    @Query(value = """
        SELECT
          st.status,
          COUNT(oc.id_chamado) as quantidade
        FROM status st
          LEFT JOIN ocorrencia_ct oc ON st.id_item_chamado = oc.id
        WHERE
          st.data BETWEEN :dataInicial and :dataFinal AND st.status = 'AVALIADO'
        GROUP BY st.status""", nativeQuery = true)
    KpisPrincipal qtdeDeItensAvaliados(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);

}
