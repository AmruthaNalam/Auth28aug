package com.auth._workfoxtech.controller;

import com.auth._workfoxtech.dto.EmployeeRequestDto;
import com.auth._workfoxtech.dto.JwtAuthenticationResponse;
import com.auth._workfoxtech.dto.SignUpRequest;
import com.auth._workfoxtech.dto.SinginRequest;
import com.auth._workfoxtech.entitiy.Employee;
import com.auth._workfoxtech.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> Signin(@RequestBody SinginRequest singinRequest){
        return new ResponseEntity<>(employeeService.signin(singinRequest),HttpStatus.ACCEPTED);
    }
    @PostMapping("/signup")
    public ResponseEntity<Employee> signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        return new ResponseEntity<>(employeeService.signUp(signUpRequest), HttpStatus.CREATED);
    }

//    @GetMapping("/refresh")
//    public ResponseEntity<JwtAuthenticationResponse> refreshtoken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest){
//        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
//    }


}
