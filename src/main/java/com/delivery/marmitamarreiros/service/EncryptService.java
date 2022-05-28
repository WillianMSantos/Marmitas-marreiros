package com.delivery.marmitamarreiros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {

   @Autowired
    public PasswordEncoder passwordEncoder;

    public String encryptPassword(String password) {return passwordEncoder.encode(password);}

    public boolean passwordValid(String password, String hash){
        return passwordEncoder.matches(password, hash);
    }


}
