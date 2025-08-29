package com.auth._workfoxtech.services;

import com.auth._workfoxtech.dto.*;
import com.auth._workfoxtech.entitiy.Employee;
import com.auth._workfoxtech.util.ResponseMessage;
import com.auth._workfoxtech.util.SigninResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface EmployeeService {



     ResponseEntity<ResponseMessage>  signUp(SignUpRequest signUpRequest);

     ResponseEntity<ResponseMessage>  refreshToken(RefreshTokenRequest refreshTokenRequest);

     ResponseEntity<ResponseMessage>  signin(SinginRequest singinRequest);

}
