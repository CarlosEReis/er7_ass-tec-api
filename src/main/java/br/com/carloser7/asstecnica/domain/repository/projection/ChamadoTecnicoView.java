package br.com.carloser7.asstecnica.domain.repository.projection;

import java.time.LocalDateTime;

public interface ChamadoTecnicoView {

    Integer getId();
    LocalDateTime getDataCriacao();
    String getStatus();

    ClienteView getCliente();
    interface ClienteView {
        Integer getId();
        String getNome();
        String getDocumento();
    }

    /* Esta abordagem, retorn apenas as propriedade de cliente listadas abaixo, porém monta a
    query SQL com todos as propriedades de cliente, conforme abaixo:
        SELECT
            c1_0.id, c1_0.dtcriacao, c1_0.status,
            c2_0.id,c2_0.documento,c2_0.email,c2_0.cep,c2_0.logradouro,c2_0.numero,
            c2_0.complemento,c2_0.bairro,c2_0.cidade,c2_0.estado,c2_0.fantasia,
            c2_0.inscricao,c2_0.nome,c2_0.tabela_preco,c2_0.telefone,c2_0.tipo_pessoa
        FROM chamado_tec c1_0
        LEFT JOIN cliente c2_0
        ON c2_0.id = c1_0.id_cliente
        LIMIT ?,?

     Em uma abordagem diferente, onde os atributos estivessen no mesmo objeto(na mesma hierarquia), a query
     teria apenas as propriedades necessárias para montar a projeção, conforme abaixo:

     public interface ChamadoTecnicoView {
        Integer getId();
        LocalDateTime getDataCriacao();
        String getStatus();

        Integer getClienteId();
        String getClienteNome();
        String getClienteDocumento();
     }
        SELECT
          c1_0.id,
          c1_0.dtcriacao,
          c1_0.status,
          c1_0.id_cliente,
          c2_0.nome,
          c2_0.documento
        FROM chamado_tec c1_0
        LEFT JOIN cliente c2_0
          ON c2_0.id = c1_0.id_cliente */
}
