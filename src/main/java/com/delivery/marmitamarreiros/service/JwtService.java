package com.delivery.marmitamarreiros.service;

import com.delivery.marmitamarreiros.MarmitamarreirosApplication;
import com.delivery.marmitamarreiros.model.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Auth usuario) {
        long expString = Long.parseLong(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instante = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instante);

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
    }

    public boolean tokenIsValid(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiration = claims.getExpiration();
            LocalDateTime data = dataExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MarmitamarreirosApplication.class);
        JwtService service = context.getBean(JwtService.class);
        Auth usuario = Auth.builder().email("fulano@test").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);

        boolean tokenValido = service.tokenIsValid(token);
        System.out.println("Token válido: " + tokenValido);

        String login = service.obterLoginUsuario(token);
        System.out.println("Login do usuário: " + login);

    }
}
