package com.delivery.marmitamarreiros.repository;

import com.delivery.marmitamarreiros.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer> {

    Optional<Auth> findByEmail(String email);


}