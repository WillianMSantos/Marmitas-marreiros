package com.delivery.marmitamarreiros.security;

import com.delivery.marmitamarreiros.service.AuthService;
import com.delivery.marmitamarreiros.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private AuthService usuarioService;

    public JwtAuthFilter(JwtService jwtService, AuthService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenIsValid(token);
            if(isValid){
                String loginUsuario = jwtService.obterLoginUsuario(token);
                UserDetails userDetails = usuarioService.loadUserByUsername(loginUsuario);
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
