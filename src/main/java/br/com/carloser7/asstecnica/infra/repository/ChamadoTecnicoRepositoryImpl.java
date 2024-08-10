package br.com.carloser7.asstecnica.infra.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.*;
import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.model.StatusChamadoTecnico;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ChamadoTecnicoRepositoryImpl implements ChamadoTecnicoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<QtdeChamadoAbertosFechados> chamadosAbertosFechados(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType tipo) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(Object[].class);
        var root = query.from(ChamadoTecnico.class);

        Expression<String> functionDate = null;

        if (DateFitlterType.ANO.equals(tipo))
            functionDate = builder.function("YEAR", String.class, root.get("dataCriacao"));

        if (DateFitlterType.MES.equals(tipo))
            functionDate = builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-%m-01"));

        var predicates = new ArrayList<Predicate>();

        if (dataInicial != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicial));

        if (dataFinal != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), dataFinal));

        Expression<Long> caseExpression = builder.selectCase()
                .when(builder.equal(root.get("status"), "FINALIZADO"), 1L)
                .otherwise(0L).as(Long.class);

        query.multiselect(
                functionDate,
                builder.count(root.get("id")),
            builder.sum(caseExpression)
        );

        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDate);

        List<Object[]> results = manager.createQuery(query).getResultList();

        return results.stream()
            .map(result -> new QtdeChamadoAbertosFechados(
                    (String) result[0],
                    (String) result[1],
                    (Long) result[2]))
            .collect(Collectors.toList());
    }

    @Override
    public ConsultaEstatisticaAbertosFechados chamadosAbertosFechadosNovo(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType tipo) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(Object[].class);
        var root = query.from(ChamadoTecnico.class);

        Expression<String> functionDate = null;

        if (DateFitlterType.ANO.equals(tipo))
            functionDate = builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-01-01"));;

        if (DateFitlterType.MES.equals(tipo)) {
            dataFinal = dataFinal.plusDays(1);
            functionDate = builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-%m-01"));
        }

        if (DateFitlterType.DIA.equals(tipo))
            functionDate = builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-%m-%d"));

        var predicates = new ArrayList<Predicate>();
        predicates.add(builder.and(root.get("status").in(StatusChamadoTecnico.FILA, StatusChamadoTecnico.FINALIZADO, StatusChamadoTecnico.PROCESSANDO)));

        if (dataInicial != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicial));

        if (dataFinal != null)
            predicates.add(builder.lessThan(root.get("dataCriacao"), dataFinal));

        query.multiselect(
            functionDate,
            root.get("status"),
            builder.count(root.get("id")));

        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDate, root.get("status"));
        List<Object[]> results = manager.createQuery(query).getResultList();

        var consulta = new ConsultaEstatisticaAbertosFechados(
                dataInicial,
                dataFinal,
                tipo,
                LocalDateTime.now(),
                results.stream()
                        .map(result -> new QtdeChamadoAbertosFechados (
                                (String) result[0],
                                result[1].toString(),
                                (Long) result[2]))
                        .collect(Collectors.toList())
        );

        return consulta;
    }


    @Override
    public List<kpisPrincipais> kpisPrincipais(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(kpisPrincipais.class);
        var root = query.from(ChamadoTecnico.class);

        var predicates = new ArrayList<Predicate>();

        if (dataInicial != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicial));

        if (dataFinal != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), dataFinal));

        var selection = builder.construct(
                kpisPrincipais.class,
                builder.function("DATE", LocalDate.class, root.get("dataCriacao")),
                root.get("status"),
                builder.count(root.get("id")));

        var expressions = new ArrayList<Expression>();
        expressions.add(builder.function("YEAR", LocalDate.class, root.get("dataCriacao")));
        expressions.add(builder.function("MONTH", LocalDate.class, root.get("dataCriacao")));
        expressions.add(root.get("status"));

        query.select(selection);
        query.groupBy(expressions.toArray(new Expression[0]));

        /*query.groupBy(builder.function("YEAR", LocalDate.class, root.get("dataCriacao")));
        query.groupBy(builder.function("MONTH", LocalDate.class, root.get("dataCriacao")));
        query.groupBy(root.get("status"));*/
        query.where(predicates.toArray(new Predicate[0]));
        return manager.createQuery(query).getResultList();
    }

    public ConsultaEstatistica kpisPrincipalImpl(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(Object[].class);
        var root = query.from(ChamadoTecnico.class);

        query.multiselect(
            root.get("status").alias("status"),
            builder.sum(
                builder.selectCase()
                    .when(builder.equal(builder.function("YEAR", Integer.class, root.get("dataCriacao")), dataBase.getYear()), 1L)
                    .otherwise(0).as(Long.class)
            ).alias("anoBase"),
            builder.sum(
                builder.selectCase()
                    .when(builder.equal(builder.function("YEAR", Integer.class, root.get("dataCriacao")), dataConfronto.getYear()), 1L)
                    .otherwise(0).as(Long.class)
            ).alias("anoConfronto")
        );

        query.groupBy(root.get("status"));

        List<Object[]> resultList = manager.createQuery(query).getResultList();

        List<resultadoKpis> collect = resultList
                .stream()
                .map(row -> new resultadoKpis(
                        StatusChamadoTecnico.valueOf(String.valueOf(row[0])),
                        (Long) row[1],
                        (Long) row[2]
                ))
                .collect(Collectors.toList());

        var consulta = new ConsultaEstatistica(
                dataBase,
                dataConfronto,
                DateFitlterType.ANO,
                LocalDateTime.now(),
                collect
        );

        return consulta;
    }

    public ConsultaEstatistica kpisPrincipalImplDOIS(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(Object[].class);
        var root = query.from(ChamadoTecnico.class);

        Expression<String> stringParaFiltro = null;
        String dataFormat = "";

        Expression<Long> caseDataBase = null;
        Expression<Long> caseDataConfronto = null;

        if (tipoFiltro.equals(DateFitlterType.MES)) {
            dataConfronto = dataBase.minusMonths(1);
            stringParaFiltro = builder.literal("%Y-%m");
            dataFormat = "yyyy-MM";
            Expression<String> functionDateFormat = builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-%m"));

            caseDataBase =
                builder.selectCase()
                    .when(builder.equal(functionDateFormat, dataBase.format(DateTimeFormatter.ofPattern(dataFormat))), 1L)
                    .otherwise(0).as(Long.class);

            caseDataConfronto = builder.selectCase()
                    .when(builder.equal(functionDateFormat, dataConfronto.format(DateTimeFormatter.ofPattern(dataFormat))), 1L)
                    .otherwise(0).as(Long.class);

            /**
             * # MES
             * select
             *     ct1_0.status,
             *     sum(case when DATE_FORMAT(ct1_0.dtcriacao, '%Y-%m')='2023-08' then 1 else 0 end) AS Qtde_2023,
             *     sum(case when DATE_FORMAT(ct1_0.dtcriacao, '%Y-%m')='2023-07' then 1 else 0 end) AS Qtde_2022
             * from
             *     chamado ct1_0
             *     group by
             *         1
             */
        }

        if (tipoFiltro.equals(DateFitlterType.ANO)) {
            stringParaFiltro = builder.literal("%Y");
            dataFormat = "yyyy-MM";

            dataConfronto = LocalDate.of(dataConfronto.getYear(), dataBase.getMonth(), 1);

            var dtBase = dataBase.format(DateTimeFormatter.ofPattern(dataFormat));
            var dtConfronto = dataConfronto.format(DateTimeFormatter.ofPattern(dataFormat));

            var primeiroDiaDataBase = LocalDate.of(dataBase.getYear(), Month.JANUARY, 1).format(DateTimeFormatter.ofPattern(dataFormat));
            var primeiroDiaDataConfronto = LocalDate.of(dataConfronto.getYear(), Month.JANUARY, 1).format(DateTimeFormatter.ofPattern(dataFormat));


            caseDataBase = builder.selectCase()
                    .when(
                            builder.between(builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-%m")), primeiroDiaDataBase, dtBase),
                            1L)
                    .otherwise(0).as(Long.class);

            caseDataConfronto = builder.selectCase()
                    .when(
                            builder.between(builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-%m")), primeiroDiaDataConfronto, dtConfronto),
                            1L)
                    .otherwise(0).as(Long.class);

            /**
             * # YTD
             * select
             *     ct1_0.status,
             *     sum(case when DATE_FORMAT(ct1_0.dtcriacao, '%Y-%m') >= '2022-01' AND DATE_FORMAT(ct1_0.dtcriacao, '%Y-%m') <= '2022-08' then 1 else 0 end) AS Qtde_2023,
             *     sum(case when DATE_FORMAT(ct1_0.dtcriacao, '%Y-%m') >= '2021-01' AND DATE_FORMAT(ct1_0.dtcriacao, '%Y-%m') <= '2021-08' then 1 else 0 end) AS Qtde_2022
             * from
             *     chamado ct1_0
             *     group by
             *         1
             */
        }

        query.multiselect(
                root.get("status").alias("status"),
                builder.sum(
                    caseDataBase
                ).alias("anoBase"),
                builder.sum(
                    caseDataConfronto)
                .alias("anoConfronto")
        );

        /*query.multiselect(
                root.get("status").alias("status"),
                builder.sum(
                        builder.selectCase()
                                .when(builder.equal(functionDateFormat, dataBase.format(DateTimeFormatter.ofPattern(dataFormat))), 1L)
                                .otherwise(0).as(Long.class)
                ).alias("anoBase"),
                builder.sum(
                        builder.selectCase()
                                .when(builder.equal(functionDateFormat, dataConfronto.format(DateTimeFormatter.ofPattern(dataFormat))), 1L)
                                .otherwise(0).as(Long.class)
                ).alias("anoConfronto")
        );*/

        query.groupBy(root.get("status"));

        List<Object[]> resultList = manager.createQuery(query).getResultList();

        List<resultadoKpis> collect = resultList
                .stream()
                .map(row -> new resultadoKpis(
                        StatusChamadoTecnico.valueOf(String.valueOf(row[0])),
                        (Long) row[1],
                        (Long) row[2]
                ))
                .collect(Collectors.toList());

        var consulta = new ConsultaEstatistica(
                dataBase,
                dataConfronto,
                tipoFiltro,
                LocalDateTime.now(),
                collect
        );

        return consulta;

    }

    @Override
    public ConsultaEstatisticaAbertosFechados kpisPrincipalImplAllDOIS(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro) {
        return null;
    }


}
