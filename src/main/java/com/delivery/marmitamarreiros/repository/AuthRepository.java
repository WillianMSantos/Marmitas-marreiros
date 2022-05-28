package com.delivery.marmitamarreiros.repository;

import com.delivery.marmitamarreiros.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Auth findByEmail(String email);
}
