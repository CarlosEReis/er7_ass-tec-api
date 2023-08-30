package br.com.carloser7.asstecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carloser7.asstecnica.security.TokenService;
import br.com.carloser7.asstecnica.security.UsuarioSistema;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsuarioDTO user) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((UsuarioSistema) authenticate.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

class LoginResponseDTO {

    private String token;

    LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
} 

class UsuarioDTO {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}