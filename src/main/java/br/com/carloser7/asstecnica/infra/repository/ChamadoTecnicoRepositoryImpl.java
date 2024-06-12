package br.com.carloser7.asstecnica.infra.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.DateFitlterType;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.QtdeChamadoAbertosFechados;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.kpisPrincipais;
import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

        if (DateFitlterType.DIA.equals(tipo))
            functionDate = builder.function("DATE_FORMAT", String.class, root.get("dataCriacao"), builder.literal("%Y-%m-%d"));

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
                    LocalDate.parse((String) result[0]),
                    (Long) result[1],
                    (Long) result[2]))
            .collect(Collectors.toList());
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


}
