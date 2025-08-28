package com.auth._workfoxtech.services.ServiceImpl;

import com.auth._workfoxtech.dto.*;
import com.auth._workfoxtech.entitiy.Employee;
import com.auth._workfoxtech.repository.EmployeeRepository;
import com.auth._workfoxtech.services.EmployeeService;
import com.auth._workfoxtech.services.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    private final JwtService jwtService;



    @Override
    public Employee signUp(SignUpRequest signUpRequest) {
        if (employeeRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username Already Exists");
        }
        Employee employee=new Employee();
//        Employee employee=Employee.build( signUpRequest.getFirstname(),signUpRequest.getLastname(),signUpRequest.getEmail(),passwordEncoder.encode(signUpRequest.getPassword()),Role.USER);
        employee.setEmail(signUpRequest.getEmail());
        employee.setName(signUpRequest.getName());

        employee.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        employee.setRole(Role.USER);
        return employeeRepository.save(employee);
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail=jwtService.extractUsername(refreshTokenRequest.getToken());
        Employee employee=employeeRepository.findByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("please provide valid token"));
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),employee)){
            var jwt=jwtService.generateToken(employee);
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshtoken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }




    @Override
    public JwtAuthenticationResponse signin(SinginRequest singinRequest) {
        try {
            System.out.println("Hello " + singinRequest);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(singinRequest.getEmail(), singinRequest.getPassword())
            );
            System.out.println("Authentication successful");
        } catch (AuthenticationException ex) {
            System.out.println("Authentication failed: " + ex.getMessage());
            throw ex; // or return a custom error response
        }

        System.out.println("Hello1");
        var employee=employeeRepository.findByEmail(singinRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Please Provide Valid Email or Password"));
        System.out.println("Hello2");
        var jwt=jwtService.generateToken(employee);
        System.out.println("Hello3");
        var refreshtoken=jwtService.generateRefreshToken(new HashMap<>(), employee);
        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshtoken(refreshtoken);
        System.out.println("Hello4");
        return jwtAuthenticationResponse;
    }
}
