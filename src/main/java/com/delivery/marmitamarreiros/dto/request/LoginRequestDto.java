package com.delivery.marmitamarreiros.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class LoginRequestDto {

    private String email;
    private String senha;

    public boolean isNullOrEmpty() {
        return this.getEmail() == null || this.getSenha() == null ||
                this.getEmail().isEmpty() || this.getSenha().isEmpty();
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
