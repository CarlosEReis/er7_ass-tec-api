package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TopProdutoEstatistica extends ConsultEstatistica {

    private final List<TopProdutos> itens;

    public TopProdutoEstatistica(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro, LocalDateTime dataConsulta, List<TopProdutos> itens) {
        super(dataBase, dataConfronto, tipoFiltro, dataConsulta);
        this.itens = itens;
    }

    public List<TopProdutos> getItens() {
        return itens;
    }
}