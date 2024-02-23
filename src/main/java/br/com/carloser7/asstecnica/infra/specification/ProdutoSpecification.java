package br.com.carloser7.asstecnica.infra.specification;

import br.com.carloser7.asstecnica.domain.filter.ProdutoFilter;
import br.com.carloser7.asstecnica.domain.model.Produto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class ProdutoSpecification {

    public static Specification<Produto> comFiltro(ProdutoFilter produtoFilter) {
        return (root, query, builder) -> {

            var predicates = new ArrayList<Predicate>();

            if (StringUtils.hasText(produtoFilter.sku()))
                predicates.add(builder.equal(root.get("sku"), produtoFilter.sku()));

            if (StringUtils.hasText(produtoFilter.nome()))
                predicates.add(builder.like(root.get("nome"), "%" + produtoFilter.nome() + "%"));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
