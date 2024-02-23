package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.domain.filter.TopClientesFilter;
import br.com.carloser7.asstecnica.domain.filter.TopProdutoFilter;
import br.com.carloser7.asstecnica.domain.filter.TopTecnicosFilter;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepository;
import br.com.carloser7.asstecnica.domain.repository.ClienteRepository;
import br.com.carloser7.asstecnica.domain.repository.ProdutoRepository;
import br.com.carloser7.asstecnica.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EstatisticasController {

    @Autowired
    private ChamadoTecnicoRepository chamadoTecnicoRepository;

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    /*@GetMapping("/estatisticas/kpis-principal")
    public List<KpisPrincipal> kpisPrincipais() {
        return this.chamadoTecnicoRepository.kpisPrincipais(Year.now());
    }*/

    @GetMapping("/estatisticas/produtos/top-mais-defeitos")
    public List<?> top4ProdutosComDefeito(TopProdutoFilter filter) {
        return this.produtoRepository.topProdutos(filter);
    }

    @GetMapping("/estatisticas/clientes/top-mais-chamados")
    public List<?> top3ClienteComMaisChamados(TopClientesFilter filter) {
        return this.clienteRepository.topClientes(filter);
    }

    @GetMapping("/estatisticas/tecnicos/top-mais-chamados")
    public List<?> top3TecnicosMaisFinalizaraChamados(TopTecnicosFilter filter) {
        return this.usuarioRepository.topUsuarios(filter);
    }

    /*@GetMapping("/estatisticas/status-chamado-pordia")
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
