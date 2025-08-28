package com.auth._workfoxtech.services;

import com.auth._workfoxtech.dto.*;
import com.auth._workfoxtech.entitiy.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface EmployeeService {



     Employee signUp(SignUpRequest signUpRequest);

     JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

     JwtAuthenticationResponse signin(SinginRequest singinRequest);

}
