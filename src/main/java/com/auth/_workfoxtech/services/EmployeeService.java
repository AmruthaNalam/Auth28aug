package com.auth._workfoxtech.services;

import com.auth._workfoxtech.dto.*;
import com.auth._workfoxtech.util.ResponseMessage;
import com.auth._workfoxtech.util.SigninResponse;
import org.springframework.http.ResponseEntity;
public interface EmployeeService {
     ResponseEntity<ResponseMessage>  signUp(SignUpRequest signUpRequest);
     ResponseEntity<ResponseMessage>  refreshToken(RefreshTokenRequest refreshTokenRequest);
     ResponseEntity<SigninResponse>  signin(SinginRequest singinRequest);
     ResponseEntity<ResponseMessage> updatePassword(String email,UpdatePasswordRequest updatePasswordRequest);
}
