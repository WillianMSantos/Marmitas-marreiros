package com.delivery.marmitamarreiros.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    private String email;
    private String nome;
    private String senha;

    public boolean isNullOrEmpty() {
        return this.getEmail() == null || this.getSenha() == null || this.getNome() == null ||
                this.getEmail().isEmpty() || this.getSenha().isEmpty() || this.getNome().isEmpty();
    }
}
