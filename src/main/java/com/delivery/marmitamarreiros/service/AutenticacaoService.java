package com.delivery.marmitamarreiros.service;

import com.delivery.marmitamarreiros.dto.request.RegisterRequestDto;
import com.delivery.marmitamarreiros.exception.ExistingEmailException;
import com.delivery.marmitamarreiros.exception.InvalidDataException;
import com.delivery.marmitamarreiros.model.Usuario;
import com.delivery.marmitamarreiros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public void userRegistration(RegisterRequestDto request) {
        if (request.isNullOrEmpty()) {
            throw new InvalidDataException();
        }

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ExistingEmailException();
        }

        Usuario user = new Usuario();
        user.setEmail(request.getEmail());
        user.setNome(request.getNome());
        user.setSenha(new BCryptPasswordEncoder().encode(request.getSenha()));
        usuarioRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent()){
            return usuario.get();

        }
        throw new UsernameNotFoundException("Dados Invalidos");
    }
}