package br.com.carloser7.asstecnica.domain.repository;

import java.util.List;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.*;
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

    @Query(value = """
        WITH status AS (
          SELECT 'FILA' AS status
          UNION ALL SELECT 'PROCESSANDO'
          UNION ALL SELECT 'FINALIZADO'
        )
        SELECT
            s.status, COUNT(t.status) AS quantidade
        FROM status s
            LEFT JOIN chamado t ON s.status = t.status
        GROUP BY s.status;""", nativeQuery = true)
    List<KpisPrincipal> kpisPrincipais();

    @Query(value = """
        SELECT
            p.sku,
            count(id_produto) AS quantidade
        FROM
        ocorrencia_ct o JOIN produto p
            ON o.id_produto = p.id
        GROUP BY id_produto
        ORDER BY quantidade desc
        LIMIT 4;""", nativeQuery = true)
    List<Top4Produtos> top4ProdutoDefeito();

    @Query(value = """
        SELECT
            cl.nome,
            COUNT(ch.id_cliente) AS quantidade
        FROM chamado ch
            LEFT JOIN cliente cl
            ON ch.id_cliente = cl.id
        GROUP BY cl.nome
        ORDER BY quantidade DESC
        LIMIT 3""", nativeQuery = true)
    List<Top3ClienteMaisChamados> top3ClienteComMaisChamados();

    @Query(value = """
        SELECT
            st.usuario AS tecnico,
            COUNT(ch.id) AS quantidade
        FROM chamado ch
            LEFT JOIN status st
            ON ch.id = st.id_chamado AND st.status = 'FINALIZADO'
        WHERE id_chamado IS NOT NULL
        GROUP BY st.usuario
        ORDER BY quantidade DESC
        LIMIT 3""", nativeQuery = true)
    List<Top3Tecnicos> top3TecnicosMaisFinalizaraChamados();

    @Query(value = """
       SELECT
         DATE(st.data) AS data,
         st.status,
         COUNT(ch.id) AS quantidade
       FROM chamado ch
           LEFT JOIN status st
           ON ch.id = st.id_chamado AND st.status IN ('FILA', 'FINALIZADO')
       WHERE
         id_chamado IS NOT NULL
         AND
         MONTH(st.data) = MONTH(CURRENT_DATE())
       GROUP BY DATE(st.data), st.status""", nativeQuery = true)
    List<StatusChamadoDia> statusChamadosPorDia();

    @Query(value = """
        WITH Meses AS (
          SELECT 1 AS mes
          UNION ALL SELECT 2
          UNION ALL SELECT 3
          UNION ALL SELECT 4
          UNION ALL SELECT 5
          UNION ALL SELECT 6
          UNION ALL SELECT 7
          UNION ALL SELECT 8
          UNION ALL SELECT 9
          UNION ALL SELECT 10
          UNION ALL SELECT 11
          UNION ALL SELECT 12
        )

        SELECT
            m.mes,
            status.status AS status,
            COUNT(CASE WHEN MONTH(st.data) = m.mes AND st.status = status.status THEN 1 ELSE null END) AS quantidade
        FROM Meses m
        CROSS JOIN (SELECT 'FILA' AS status UNION ALL SELECT 'FINALIZADO') status
        LEFT JOIN status st ON month(st.data) = m.mes AND YEAR(st.data) = YEAR(CURRENT_DATE())
        GROUP
            BY m.mes,
            status.status""", nativeQuery = true)
    List<StatusChamadoMes> statusChamadosPorMes();

    @Query(value = """
        SELECT
          st.status,
          COUNT(oc.id_chamado) as quantidade
        FROM status st
          LEFT JOIN ocorrencia_ct oc ON st.id_item_chamado = oc.id
        WHERE
          st.status = 'AVALIADO'
        GROUP BY st.status""", nativeQuery = true)
    KpisPrincipal qtdeDeItensAvaliados();
}
