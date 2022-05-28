package com.delivery.marmitamarreiros.service;

import com.delivery.marmitamarreiros.dto.request.LoginRequestDto;
import com.delivery.marmitamarreiros.dto.request.RegisterRequestDto;
import com.delivery.marmitamarreiros.exception.ExistingEmailException;
import com.delivery.marmitamarreiros.exception.InvalidDataException;
import com.delivery.marmitamarreiros.exception.UserNotFoundException;
import com.delivery.marmitamarreiros.exception.WrongPasswordException;
import com.delivery.marmitamarreiros.model.Auth;
import com.delivery.marmitamarreiros.repository.AuthRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    EncryptService encryptService;

    public void userRegister(RegisterRequestDto registerRequest) {

        if(registerRequest.isNullOrEmpty()) {
            throw new InvalidDataException();
        }

        if(authRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new ExistingEmailException();
        }

        val user = new Auth();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(encryptService.encryptPassword(registerRequest.getPassword()));
        authRepository.save(user);
    }

    public String loginUser(LoginRequestDto requestDto) {

        if (requestDto.isNullOrEmpty()){
            throw new InvalidDataException();
        }

        Auth user = authRepository.findByEmail(requestDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!encryptService.passwordValid(requestDto.getPassword(), user.getPassword())){
             throw new WrongPasswordException();
        }

        return jwtService.generateJwt(requestDto);
    }

}
