package com.delivery.marmitamarreiros.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
    private String jwt;
    private String prefix;

    public LoginResponseDto(String jwt, String prefix) {
        this.jwt = jwt;
        this.prefix = prefix;
    }
}
