package br.com.carloser7.assistenciatecnicaapi.util;

import br.com.carloser7.asstecnica.domain.model.Permissao;
import br.com.carloser7.asstecnica.domain.model.Roles;
import br.com.carloser7.asstecnica.domain.model.Usuario;
import br.com.carloser7.asstecnica.domain.repository.PermissaoRepository;
import br.com.carloser7.asstecnica.domain.repository.UsuarioRepository;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.restassured.RestAssured.given;

@Component
public class AutenticacaoUtil {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PermissaoRepository permissaoRepository;

    public  void criaUsuarios() {
        criaPermissoes();

        var usuario1 = new Usuario();
        usuario1.setNome("Ana");
        usuario1.setEmail("ana@admin.com");
        usuario1.setSenha(passwordEncoder.encode("ana@senha@teste"));
        Permissao permissao1 = new Permissao();
        permissao1.setId(1);
        usuario1.setPermissoes(List.of(permissao1));
        usuarioRepository.save(usuario1);


        var usuario2 = new Usuario();
        usuario2.setNome("Bia");
        usuario2.setEmail("bia@gestora.com");
        usuario2.setSenha(passwordEncoder.encode("bia@senha@teste"));
        Permissao permissao2 = new Permissao();
        permissao2.setId(2);
        usuario2.setPermissoes(List.of(permissao2));
        usuarioRepository.save(usuario2);

        var usuario3 = new Usuario();
        usuario3.setNome("Carol");
        usuario3.setEmail("carol@tecnica.com");
        usuario3.setSenha(passwordEncoder.encode("carol@senha@teste"));
        Permissao permissao3 = new Permissao();
        permissao3.setId(3);
        usuario3.setPermissoes(List.of(permissao3));
        usuarioRepository.save(usuario3);
    }

    public String geraTokenUsuarioComRole(Roles role) {
        String jsonUserRole = buscaUsuarioComRole(role);
        AutenticacaoResponse authResponse =
            given()
                .basePath("/auth/login")
                .body(jsonUserRole)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
                .body()
                .as(AutenticacaoResponse.class);
        return authResponse.getToken();
    }

    private void criaPermissoes() {
        var p1 = new Permissao();
        p1.setNome("ROLE_ADMIN");

        var p2 = new Permissao();
        p2.setNome("ROLE_GESTOR");

        var p3 = new Permissao();
        p3.setNome("ROLE_TECNICO");

        permissaoRepository.saveAll(List.of(p1, p2, p3));
    }

    private String buscaUsuarioComRole(Roles role) {
        String fileName = role.toString().split("_")[1].toLowerCase().concat(".json");
        String pathUserRole = "/json/correto/users/".concat(fileName);
        return ResourceUtils.getContentFromResource(pathUserRole);
    }

}

class AutenticacaoResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
