package com.delivery.marmitamarreiros.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    private String email;
    private String name;
    private String password;

    public boolean isNullOrEmpty() {
        return this.getEmail() == null || this.getPassword() == null || this.getName() == null ||
                this.getEmail().isEmpty() || this.getPassword().isEmpty() || this.getName().isEmpty();
    }
}
