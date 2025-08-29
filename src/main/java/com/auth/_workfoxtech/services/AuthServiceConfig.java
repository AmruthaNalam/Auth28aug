package com.auth._workfoxtech.services;


import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthServiceConfig {

    UserDetailsService userDetailsService();
}
