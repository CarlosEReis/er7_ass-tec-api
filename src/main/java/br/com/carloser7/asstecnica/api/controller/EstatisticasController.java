package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.domain.filter.TopProdutoFilter;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepository;
import br.com.carloser7.asstecnica.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EstatisticasController {

    @Autowired
    private ChamadoTecnicoRepository chamadoTecnicoRepository;

    @Autowired ProdutoRepository produtoRepository;

    /*@GetMapping("/estatisticas/kpis-principal")
    public List<KpisPrincipal> kpisPrincipais() {
        return this.chamadoTecnicoRepository.kpisPrincipais(Year.now());
    }*/

    @GetMapping("/estatisticas/produtos/top-mais-defeitos")
    public List<?> top4ProdutosComDefeito(TopProdutoFilter dataFilter) {
        return this.produtoRepository.topProdutos(dataFilter);
    }

    /*@GetMapping("/estatisticas/clientes/top-mais-chamados")
    public List<?> top3ClienteComMaisChamados() {
        return this.chamadoTecnicoRepository.top3ClienteComMaisChamados(Year.now());
    }

    @GetMapping("/estatisticas/tecnicos/top-mais-chamados")
    public List<?> top3TecnicosMaisFinalizaraChamados() {
        return this.chamadoTecnicoRepository.top3TecnicosMaisFinalizaramChamados(Year.now());
    }

    @GetMapping("/estatisticas/status-chamado-pordia")
    public List<?> statusChamadosPorDia() {
        return this.chamadoTecnicoRepository.statusChamadosPorDia();
    }

    @GetMapping("/estatisticas/status-chamado-pormes")
    public List<?> statusChamadosPorMes() {
        return this.chamadoTecnicoRepository.statusChamadosPorMes(Year.now());
    }

    @GetMapping("/estatisticas/itens-avaliados")
    public KpisPrincipal qtdeItensAvaliados() {
        return this.chamadoTecnicoRepository.qtdeDeItensAvaliados(Year.now());
    }*/
}
