package com.auth._workfoxtech.services;

import com.auth._workfoxtech.dto.EmployeeRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthServiceConfig {

    UserDetailsService userDetailsService();
}
