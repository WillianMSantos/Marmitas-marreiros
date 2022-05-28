package com.delivery.marmitamarreiros.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String email;
    private String password;

    public boolean isNullOrEmpty() {
        return this.getEmail() == null || this.getPassword() == null ||
                this.getEmail().isEmpty() || this.getPassword().isEmpty();
    }
}
