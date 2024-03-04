package br.com.carloser7.asstecnica.infra.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.DateFitlterType;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.QtdeChamadoAbertosFechados;
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

@Repository
public class ChamadoTecnicoRepositoryImpl implements ChamadoTecnicoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<QtdeChamadoAbertosFechados> chamadosAbertosFechados(LocalDate dataInicial, LocalDate dataFinal, DateFitlterType tipo) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(QtdeChamadoAbertosFechados.class);
        var root = query.from(ChamadoTecnico.class);

        var functionDate = builder.function("DATE", LocalDate.class, root.get("dataCriacao"));

        if (DateFitlterType.ANO.equals(tipo))
            query.groupBy(
                builder.function("YEAR", LocalDate.class, root.get("dataCriacao")));

        if (DateFitlterType.MES.equals(tipo))
            query.groupBy(
                builder.function("MONTH", LocalDate.class, root.get("dataCriacao")));

        if (DateFitlterType.DIA.equals(tipo))
            query.groupBy(
                builder.function("DAY", LocalDate.class, root.get("dataCriacao")));

        var predicates = new ArrayList<Predicate>();

        if (dataInicial != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicial));

        if (dataFinal != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), dataFinal));

        Expression<Long> caseExpression = builder.selectCase()
            .when(builder.equal(root.get("status"), "FINALIZADO"), 1L)
            .otherwise(0L).as(Long.class);

        var selection = builder.construct(
                QtdeChamadoAbertosFechados.class,
                functionDate,
                builder.count(root.get("id")),
                builder.sum(caseExpression)
         );

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        return manager.createQuery(query).getResultList();
    }
}
