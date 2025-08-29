package com.auth._workfoxtech.controller;

import com.auth._workfoxtech.dto.*;
import com.auth._workfoxtech.entitiy.Employee;
import com.auth._workfoxtech.services.EmployeeService;
import com.auth._workfoxtech.util.ResponseMessage;
import com.auth._workfoxtech.util.SigninResponse;
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
    public ResponseEntity<ResponseMessage>  Signin(@RequestBody SinginRequest singinRequest){
        return employeeService.signin(singinRequest);
    }
    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage>  signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        return employeeService.signUp(signUpRequest);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseMessage> refreshtoken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest){
        return employeeService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/update")
    public ResponseEntity<ResponseMessage> updatePassword(@RequestParam String email,@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest){

    }


}
