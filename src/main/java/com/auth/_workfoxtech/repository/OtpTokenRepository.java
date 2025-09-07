package com.auth._workfoxtech.repository;

import com.auth._workfoxtech.entitiy.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken,Long> {

    Optional<OtpToken> findByEmail(String email);
    void deleteByEmail(String email);
}
