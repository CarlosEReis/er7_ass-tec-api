package br.com.carloser7.asstecnica.infra.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopUsuarioEstatistica;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopUsuarios;
import br.com.carloser7.asstecnica.domain.filter.TopTecnicosFilter;
import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.repository.UsuarioRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public TopUsuarioEstatistica topUsuarios(TopTecnicosFilter filtro) {

        var builder =  manager.getCriteriaBuilder();
        var query = builder.createQuery(TopUsuarios.class);
        var root = query.from(ChamadoTecnico.class);

        var statusListJoin = root.join("statusList", JoinType.LEFT);

        var predicates = new ArrayList<Predicate>();

        if (filtro.dataInicial() != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.dataInicial()));

        if (filtro.dataFinal() != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.dataFinal()));

        predicates.add(builder.equal(statusListJoin.get("status"), "FINALIZADO"));

        var selection = builder.construct(
                TopUsuarios.class,
                statusListJoin.get("nomeUsuario"),
                builder.count(root.get("id")).alias("quantidade"));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(statusListJoin.get("nomeUsuario"));
        query.orderBy(builder.desc(builder.count(statusListJoin.get("nomeUsuario"))));

        List<TopUsuarios> resultList = manager.createQuery(query).setMaxResults(filtro.top()).getResultList();

        return new TopUsuarioEstatistica(filtro.dataInicial(), filtro.dataFinal(), null, LocalDateTime.now(), resultList);
    }

}
