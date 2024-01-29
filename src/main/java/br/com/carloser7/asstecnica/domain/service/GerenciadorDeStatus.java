package br.com.carloser7.asstecnica.domain.service;

import br.com.carloser7.asstecnica.domain.exception.NegocioException;
import br.com.carloser7.asstecnica.domain.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GerenciadorDeStatus {

    private static final Roles ADMIN = Roles.ROLE_ADMIN;

    public static void setStatusChamado(ChamadoTecnico chamado, StatusChamadoTecnico status) {
        Usuario usuario = getUsuarioAtual();
        StatusChamadoTecnico finalizado = StatusChamadoTecnico.FINALIZADO;

        if (status.equals(finalizado) && usuario.naoPossuiPermissao(ADMIN))
            throw new NegocioException(
                    String.format("Operação inválida, você não tem permissao para retornar o status de chamado %s.", finalizado));

        var statusObj = new StatusChamadoObject();
        statusObj.setStatus(status);
        statusObj.setDataStatus(LocalDateTime.now());
        statusObj.setNomeUsuario(getUsuarioAtual().getNome());
        statusObj.setchamadoTecnico(chamado);

        chamado.getStatusList().add(statusObj);
    }

    public static void setStatusItemChamado(ItemChamadoTecnico item, StatusItemChamadoTecnico statusItem) {
        Usuario usuario = getUsuarioAtual();
        StatusItemChamadoTecnico avaliado = StatusItemChamadoTecnico.AVALIADO;

        if (statusItem.equals(avaliado) && usuario.naoPossuiPermissao(ADMIN))
            throw new NegocioException(
                String.format("Operação inválida, você não tem permissao para retornar o status de um item %s.", avaliado));

        var statusObj = new StatusItemChamadoObject();
        statusObj.setStatus(statusItem);
        statusObj.setDataStatus(LocalDateTime.now());
        statusObj.setNomeUsuario(getUsuarioAtual().getNome());
        statusObj.setItemChamado(item);
        item.getStatus().add(statusObj);
    }

    private static Usuario getUsuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (Usuario) authentication.getDetails();
    }
}
