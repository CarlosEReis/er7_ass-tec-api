package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.DateFitlterType;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.KpisPrincipal;
import br.com.carloser7.asstecnica.domain.dto.estatisticas.kpisPrincipais;
import br.com.carloser7.asstecnica.domain.filter.TopClientesFilter;
import br.com.carloser7.asstecnica.domain.filter.TopProdutoFilter;
import br.com.carloser7.asstecnica.domain.filter.TopTecnicosFilter;
import br.com.carloser7.asstecnica.domain.repository.ChamadoTecnicoRepository;
import br.com.carloser7.asstecnica.domain.repository.ClienteRepository;
import br.com.carloser7.asstecnica.domain.repository.ProdutoRepository;
import br.com.carloser7.asstecnica.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EstatisticasController {

    @Autowired private ChamadoTecnicoRepository chamadoTecnicoRepository;
    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping("/estatisticas/kpis-principal")
    public List<kpisPrincipais> kpisPrincipais(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal, DateFitlterType filtrarPor) {
        return this.chamadoTecnicoRepository.kpisPrincipais(dataInicial, dataFinal, filtrarPor);
    }

    @GetMapping("/estatisticas/itens-avaliados")
    public KpisPrincipal qtdeItensAvaliados(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal) {
        return this.chamadoTecnicoRepository.qtdeDeItensAvaliados(dataInicial, dataFinal);
    }

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

    @GetMapping("/estatisticas/chamados-abertos-fechados")
    public List<?> statusChamados(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal, @RequestParam DateFitlterType filtrarPor) {
        return this.chamadoTecnicoRepository.chamadosAbertosFechados(dataInicial, dataFinal, filtrarPor);
    }

}
