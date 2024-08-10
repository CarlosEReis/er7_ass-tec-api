package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TopClientesEstatistica extends ConsultEstatistica {

    private List<TopClientes> itens;

    public TopClientesEstatistica(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro, LocalDateTime dataConsulta, List<TopClientes> itens) {
        super(dataBase, dataConfronto, tipoFiltro, dataConsulta);
        this.itens = itens;
    }

    public List<TopClientes> getItens() {
        return itens;
    }
}
