package br.com.carloser7.asstecnica.infra.repository;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.DateFitlterType;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopProdutoEstatistica;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.TopProdutos;
import br.com.carloser7.asstecnica.domain.filter.ProdutoFilter;
import br.com.carloser7.asstecnica.domain.filter.TopProdutoFilter;
import br.com.carloser7.asstecnica.domain.model.ItemChamadoTecnico;
import br.com.carloser7.asstecnica.domain.model.Produto;
import br.com.carloser7.asstecnica.domain.repository.ProdutoRepositoryQueries;
import br.com.carloser7.asstecnica.domain.repository.projection.ProdutoResumoProjection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<ProdutoResumoProjection> pesquisa(ProdutoFilter produtoFilter) {

        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(ProdutoResumoProjection.class);
        var root = query.from(Produto.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(produtoFilter.sku()))
            predicates.add(builder.equal(root.get("sku"), produtoFilter.sku()));

        if (StringUtils.hasText(produtoFilter.nome()))
            predicates.add(builder.like(root.get("nome"), "%" + produtoFilter.nome() + "%"));

        var selection = builder.construct(
                ProdutoResumoProjection.class,
                root.get("id"),
                root.get("sku"),
                root.get("nome"));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<TopProdutos> topProdutos(TopProdutoFilter filtro) {

        var dataInicial = LocalDate.of(2021, 01, 01);
        var dataFinal = LocalDate.of(2023, 01, 01);
        var tipo = DateFitlterType.ANO;

        var buider = manager.getCriteriaBuilder();
        var query = buider.createQuery(TopProdutos.class);
        var root = query.from(ItemChamadoTecnico.class);

        root.join("produto");
        root.join("chamadoTecnico");

        var predicates = new ArrayList<Predicate>();

        if (filtro.dataInicial() != null)
            predicates.add(
                buider.greaterThanOrEqualTo(
                    root.get("chamadoTecnico").get("dataCriacao"),
                    filtro.dataInicial()));

        if (filtro.dataFinal() != null)
            predicates.add(
                buider.lessThanOrEqualTo(
                    root.get("chamadoTecnico").get("dataCriacao"),
                    filtro.dataFinal()));

        var selection = buider.construct(
                TopProdutos.class,
                root.get("produto").get("sku"),
                buider.count(root.get("produto")).alias("quantidade"));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(root.get("produto"));
        query.orderBy(buider.desc(buider.count(root.get("produto"))));


        return manager.createQuery(query).setMaxResults(filtro.top()).getResultList();
    }

    @Override
    public TopProdutoEstatistica topProdutosNovo(TopProdutoFilter filtro) {

        LocalDate dataInicial = null;
        LocalDate dataFinal = null;

        var buider = manager.getCriteriaBuilder();
        var query = buider.createQuery(TopProdutos.class);
        var root = query.from(ItemChamadoTecnico.class);

        root.join("produto");
        root.join("chamadoTecnico");

        var predicates = new ArrayList<Predicate>();

        if (filtro.dataInicial() != null)
            predicates.add(
                    buider.greaterThanOrEqualTo(
                            root.get("chamadoTecnico").get("dataCriacao"),
                            filtro.dataInicial()));

        if (filtro.dataFinal() != null)
            predicates.add(
                    buider.lessThanOrEqualTo(
                            root.get("chamadoTecnico").get("dataCriacao"),
                            filtro.dataFinal()));

        var selection = buider.construct(
                TopProdutos.class,
                root.get("produto").get("sku"),
                buider.count(root.get("produto")).alias("quantidade"));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(root.get("produto"));
        query.orderBy(buider.desc(buider.count(root.get("produto"))));
        List<TopProdutos> resultList = manager.createQuery(query).setMaxResults(filtro.top()).getResultList();

        return new TopProdutoEstatistica(filtro.dataInicial(), filtro.dataFinal(), null   , LocalDateTime.now(), resultList);

        //return manager.createQuery(query).setMaxResults(filtro.top()).getResultList();
    }
}
