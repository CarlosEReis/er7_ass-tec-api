package br.com.carloser7.asstecnica.infra.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopUsuarios;
import br.com.carloser7.asstecnica.domain.filter.TopTecnicosFilter;
import br.com.carloser7.asstecnica.domain.model.ChamadoTecnico;
import br.com.carloser7.asstecnica.domain.repository.UsuarioRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<TopUsuarios> topUsuarios(TopTecnicosFilter usuariofilter) {

        var builder =  manager.getCriteriaBuilder();
        var query = builder.createQuery(TopUsuarios.class);
        var root = query.from(ChamadoTecnico.class);

        var statusListJoin = root.join("statusList", JoinType.LEFT);

        var predicates = new ArrayList<Predicate>();

        if (usuariofilter.dataInicial() != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), usuariofilter.dataInicial()));

        if (usuariofilter.dataFinal() != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), usuariofilter.dataFinal()));

        predicates.add(builder.equal(statusListJoin.get("status"), "FINALIZADO"));

        var selection = builder.construct(
                TopUsuarios.class,
                statusListJoin.get("nomeUsuario"),
                builder.count(root.get("id")).alias("quantidade"));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(statusListJoin.get("nomeUsuario"));
        query.orderBy(builder.desc(builder.count(statusListJoin.get("nomeUsuario"))));

        return manager.createQuery(query).setMaxResults(usuariofilter.top()).getResultList();
    }

}
