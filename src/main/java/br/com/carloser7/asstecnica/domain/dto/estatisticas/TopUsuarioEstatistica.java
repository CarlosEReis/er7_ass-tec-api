package br.com.carloser7.asstecnica.domain.dto.estatisticas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TopUsuarioEstatistica extends ConsultEstatistica{

    private final List<TopUsuarios> itens;

    public TopUsuarioEstatistica(LocalDate dataBase, LocalDate dataConfronto, DateFitlterType tipoFiltro, LocalDateTime dataConsulta, List<TopUsuarios> itens) {
        super(dataBase, dataConfronto, tipoFiltro, dataConsulta);
        this.itens = itens;
    }

    public List<TopUsuarios> getItens() {
        return itens;
    }
}
