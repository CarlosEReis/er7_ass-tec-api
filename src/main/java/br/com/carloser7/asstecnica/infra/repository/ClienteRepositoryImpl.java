package br.com.carloser7.asstecnica.infra.repository;

import br.com.carloser7.asstecnica.domain.filter.ClienteFilter;
import br.com.carloser7.asstecnica.domain.model.Cliente;
import br.com.carloser7.asstecnica.domain.repository.ClienteResitoryQueries;
import br.com.carloser7.asstecnica.domain.repository.projection.ClienteResumoProjection;
import br.com.carloser7.asstecnica.domain.repository.projection.EnderesoResumoProjection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepositoryImpl implements ClienteResitoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<ClienteResumoProjection> pesquisar(ClienteFilter clienteFilter) {

        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(ClienteResumoProjection.class);
        var root = query.from(Cliente.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(clienteFilter.nome()))
            predicates.add(builder.like(root.get("nome"), "%" + clienteFilter.nome().toLowerCase() + "%"));

        if (StringUtils.hasText(clienteFilter.documento()))
            predicates.add(builder.equal(root.get("documento"), clienteFilter.documento()));

        var enderecoRoot = root.get("endereco");
        var enderecoProjection = builder.construct(
                EnderesoResumoProjection.class,
                enderecoRoot.get("cidade"),
                enderecoRoot.get("estado"));

        var selection = builder.construct(
                ClienteResumoProjection.class,
                root.get("id"),
                root.get("nome"),
                root.get("tipoPessoa"),
                root.get("documento"),
                enderecoProjection);

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        return manager.createQuery(query).getResultList();
    }
}
