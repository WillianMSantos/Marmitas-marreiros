package com.delivery.marmitamarreiros.service;

import com.delivery.marmitamarreiros.dto.request.LoginRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateJwt(LoginRequestDto requestDto) {
        return Jwts.builder().setSubject(requestDto.getEmail())
                .setExpiration(this.generateExpirationTime())
                .signWith(this.key).compact();
    }

    private Date generateExpirationTime() {
        int minutes = 60 * 1000;
        Date currentDate = new Date();

        return new Date(currentDate.getTime() + 5 * minutes);
    }
}
