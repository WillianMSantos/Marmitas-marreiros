package com.delivery.marmitamarreiros.service;

import com.delivery.marmitamarreiros.exception.*;
import com.delivery.marmitamarreiros.model.Auth;
import com.delivery.marmitamarreiros.repository.AuthRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Auth save(Auth usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return authRepository.save(usuario);
    }

    public UserDetails autenticar(Auth usuario){
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasOk = encoder.matches(usuario.getSenha(), user.getPassword());
        if (senhasOk){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Auth usuario = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return  User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
