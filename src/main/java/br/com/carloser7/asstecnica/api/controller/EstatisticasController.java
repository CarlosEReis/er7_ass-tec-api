package br.com.carloser7.asstecnica.api.controller;

import br.com.carloser7.asstecnica.domain.dto.estatisticas.*;
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

    //@GetMapping("/estatisticas/kpis-principal")
    public List<kpisPrincipais> kpisPrincipais(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal, DateFitlterType filtrarPor) {
        return this.chamadoTecnicoRepository.kpisPrincipais(dataInicial, dataFinal, filtrarPor);
    }

    @GetMapping("/estatisticas/itens-avaliados")
    public KpisPrincipal qtdeItensAvaliados(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal) {
        return this.chamadoTecnicoRepository.qtdeDeItensAvaliados(dataInicial, dataFinal);
    }

    //@GetMapping("/estatisticas/produtos/top-mais-defeitos")
    public List<?> top4ProdutosComDefeito(TopProdutoFilter filter) {
        return this.produtoRepository.topProdutos(filter);
    }

    @GetMapping("/estatisticas/produtos/top-mais-defeitos")
    public TopProdutoEstatistica top4ProdutosComDefeitoNovo(TopProdutoFilter filter) {
        return this.produtoRepository.topProdutosNovo(filter);
    }

    @GetMapping("/estatisticas/clientes/top-mais-chamados")
    public TopClientesEstatistica top3ClienteComMaisChamados(TopClientesFilter filter) {
        return this.clienteRepository.topClientes(filter);
    }

    @GetMapping("/estatisticas/tecnicos/top-mais-chamados")
    public TopUsuarioEstatistica top3TecnicosMaisFinalizaraChamados(TopTecnicosFilter filter) {
        return this.usuarioRepository.topUsuarios(filter);
    }

    @GetMapping("/estatisticas/chamados-abertos-fechados")
    public ConsultaEstatisticaAbertosFechados statusChamados(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal, @RequestParam DateFitlterType filtrarPor) {
        return this.chamadoTecnicoRepository.chamadosAbertosFechadosNovo(dataInicial, dataFinal, filtrarPor);
    }

    // TODO: Testes
    @GetMapping("/estatisticas/kpis-principal")
    public List<Object> kpisPrincipaisTeste(@RequestParam LocalDate dataBase, @RequestParam LocalDate dataConfronto) {

        return this.chamadoTecnicoRepository.principal(dataBase, dataConfronto );
    }
    // TODO: Testes
    @GetMapping("/estatisticas/kpis-principal-all")
    public List<Object> kpisPrincipaisAllTeste(@RequestParam LocalDate dataBase, @RequestParam LocalDate dataConfronto) {

        return this.chamadoTecnicoRepository.principal(dataBase, dataConfronto );
    }

    // TODO: Testes
    @GetMapping("/estatisticas/kpis-principal-impl")
    public Object kpisPrincipaisTesteimpl(@RequestParam LocalDate dataBase, @RequestParam LocalDate dataConfronto, @RequestParam DateFitlterType tipoFiltro) {
        ConsultaEstatistica consultaEstatistica = this.chamadoTecnicoRepository.kpisPrincipalImplDOIS(dataBase, dataConfronto, tipoFiltro);

        System.out.println(consultaEstatistica);
        return consultaEstatistica;
    }
}
