package com.delivery.marmitamarreiros.controller;

import com.delivery.marmitamarreiros.dto.request.LoginRequestDto;
import com.delivery.marmitamarreiros.dto.response.TokenDto;
import com.delivery.marmitamarreiros.exception.SenhaInvalidaException;
import com.delivery.marmitamarreiros.model.Auth;
import com.delivery.marmitamarreiros.service.AuthService;
import com.delivery.marmitamarreiros.service.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um Usuário")
    public Auth save(@RequestBody @Valid Auth usuario){
        return authService.save(usuario);
    }

    @PostMapping("/auth")
    @ApiOperation("Autentica um Usuário")
    public TokenDto autenticar(@RequestBody LoginRequestDto credenciais) {
        try {
            Auth usuario = Auth.builder().email(credenciais.getEmail()).senha(credenciais.getPassword()).build();
            UserDetails usuarioAutenticado = authService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDto(usuarioAutenticado.getUsername(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}