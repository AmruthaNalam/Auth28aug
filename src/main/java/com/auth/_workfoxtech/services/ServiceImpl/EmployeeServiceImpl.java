package com.auth._workfoxtech.services.ServiceImpl;
import com.auth._workfoxtech.common.ConstantMessage;
import com.auth._workfoxtech.dto.*;
import com.auth._workfoxtech.entitiy.Employee;
import com.auth._workfoxtech.repository.EmployeeRepository;
import com.auth._workfoxtech.services.EmployeeService;
import com.auth._workfoxtech.services.JwtService;
import com.auth._workfoxtech.util.ResponseMessage;
import com.auth._workfoxtech.util.SigninResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public ResponseEntity<ResponseMessage> signUp(SignUpRequest signUpRequest) {
        if (employeeRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username Already Exists");
        }
        Employee employee=new Employee();
        employee.setEmail(signUpRequest.getEmail());
        employee.setName(signUpRequest.getName());
        employee.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        employee.setRole(Role.USER);
        employeeRepository.save(employee);
        return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_ACCOUNT_CREATED, List.of(employee),ConstantMessage.ACCOUNT_CREATED), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<ResponseMessage>  refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail=jwtService.extractUsername(refreshTokenRequest.getToken());
        Employee employee=employeeRepository.findByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("please provide valid token"));
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),employee)){
            var jwt=jwtService.generateToken(employee);
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshtoken(refreshTokenRequest.getToken());
            return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_LOGIN_SUCCESS,List.of(jwtAuthenticationResponse),ConstantMessage.LOGIN_SUCCESS),HttpStatus.ACCEPTED);
        }
        return null;
    }
    @Override
    public ResponseEntity<ResponseMessage>  signin(SinginRequest singinRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singinRequest.getEmail(), singinRequest.getPassword()));
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_UNAUTHORIZED,List.of(),ConstantMessage.BAD_CREDENTIALS),HttpStatus.UNAUTHORIZED);
        }
        var employee=employeeRepository.findByEmail(singinRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Please Provide Valid Email or Password"));
        var jwt=jwtService.generateToken(employee);
        var refreshtoken=jwtService.generateRefreshToken(new HashMap<>(), employee);
        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshtoken(refreshtoken);
        return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_LOGIN_SUCCESS,List.of(jwtAuthenticationResponse),ConstantMessage.LOGIN_SUCCESS),HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseMessage> updatePassword(String email, UpdatePasswordRequest updatePasswordRequest) {
        Employee employee=employeeRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("please provide valid token"));
        if(!passwordEncoder.matches(updatePasswordRequest.getCurrentPassword(),employee.getPassword())){
            return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_UNAUTHORIZED,List.of(),ConstantMessage.INVALID_PASSWORD),HttpStatus.UNAUTHORIZED);
        }
        if(passwordEncoder.matches(updatePasswordRequest.getNewPassword(),employee.getPassword())){
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_GATEWAY.value(), List.of(),ConstantMessage.SAME_PASSWORD),HttpStatus.BAD_REQUEST);
        }
        employee.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        employeeRepository.save(employee);
        return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_LOGIN_SUCCESS,List.of(),ConstantMessage.PASSWORD_UPDATED),HttpStatus.ACCEPTED);
    }
}
