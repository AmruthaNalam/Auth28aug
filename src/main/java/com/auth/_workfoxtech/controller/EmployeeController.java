package com.auth._workfoxtech.controller;

import com.auth._workfoxtech.dto.*;
import com.auth._workfoxtech.entitiy.Employee;
import com.auth._workfoxtech.services.EmployeeService;
import com.auth._workfoxtech.services.OtpTokenService;
import com.auth._workfoxtech.services.ServiceImpl.EmailServiceImpl;
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
    private final EmailServiceImpl emailService;
    private final OtpTokenService otpTokenService;

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse>  Signin(@RequestBody SinginRequest singinRequest){
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
        return employeeService.updatePassword(email,updatePasswordRequest);
    }

    @GetMapping("/generateOtp")
    public ResponseEntity<ResponseMessage> generateOtp(@RequestParam String email){
        return otpTokenService.generateOtp(email,emailService);
    }

    @GetMapping("/validateOtp")
    public ResponseEntity<ResponseMessage> validateOtp(@RequestBody OtpValidDto otpValidDto){
        return otpTokenService.validateOtp(otpValidDto);
    }


}
