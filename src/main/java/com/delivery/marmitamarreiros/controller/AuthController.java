package com.delivery.marmitamarreiros.controller;

import com.delivery.marmitamarreiros.dto.request.LoginRequestDto;
import com.delivery.marmitamarreiros.dto.request.RegisterRequestDto;
import com.delivery.marmitamarreiros.dto.response.DefaultMessageResponseDto;
import com.delivery.marmitamarreiros.dto.response.LoginResponseDto;
import com.delivery.marmitamarreiros.exception.ExistingEmailException;
import com.delivery.marmitamarreiros.exception.InvalidDataException;
import com.delivery.marmitamarreiros.exception.UserNotFoundException;
import com.delivery.marmitamarreiros.service.AuthService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@NotNull @RequestBody RegisterRequestDto registerRequest) {
        try{
            authService.userRegister(registerRequest);
            return ResponseEntity.ok().build();
        }
        catch (ExistingEmailException | InvalidDataException e) {
            return new ResponseEntity(new DefaultMessageResponseDto(e.getMessage(),
                    HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequest) {
        try {
            String jwt = authService.loginUser(loginRequest);
            LoginResponseDto response = new LoginResponseDto(jwt, "Bearer");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        catch (UserNotFoundException e) {
            return new ResponseEntity(new DefaultMessageResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }

        catch (Exception e) {
            return new ResponseEntity(new DefaultMessageResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST);

        }
    }

}