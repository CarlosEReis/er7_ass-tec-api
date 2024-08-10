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
import java.util.List;

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


    @Query(value = """
        SELECT
            DATE_FORMAT(c.dtcriacao, "%Y-01-01") as ano,
            c.status,
            count(c.id) as quantidade
        FROM chamado c
        WHERE YEAR(c.dtcriacao) = YEAR(:dataBase) OR YEAR(c.dtcriacao) = :dataConfronto
        GROUP BY 1, 2""", nativeQuery = true)
    List<Object> principal(@Param("dataBase") LocalDate dataBase, @Param("dataConfronto") LocalDate dataConfronto);
}
