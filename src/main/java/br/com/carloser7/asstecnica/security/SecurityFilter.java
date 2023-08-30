package br.com.carloser7.asstecnica.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AppUserDetailsSevice userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        var token = this.recoverToken(request);

        if (token != null) {
            var email = tokenService.validateToken(token);
            UsuarioSistema user = (UsuarioSistema) userDetailsService.loadUserByUsername(email);

            var authetication = new UsernamePasswordAuthenticationToken(user.getUsuario().getEmail(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authetication);

        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
    
}
