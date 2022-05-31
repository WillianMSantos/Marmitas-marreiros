package com.delivery.marmitamarreiros.config;

import com.delivery.marmitamarreiros.model.Usuario;
import com.delivery.marmitamarreiros.repository.UsuarioRepository;
import com.delivery.marmitamarreiros.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {


    private TokenService tokenService;

    private UsuarioRepository repository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);

        if (valido) {
            autenticarCliente(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Integer idUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = repository.findById(idUsuario).get();

        UsernamePasswordAuthenticationToken autentication = new UsernamePasswordAuthenticationToken(usuario,
                null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(autentication);
    }

    private String recuperarToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || token.startsWith("Beaurer")){
            return null;
        }else {
            return token.substring(7, token.length());
        }

    }
}
