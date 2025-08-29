package com.auth._workfoxtech.services.ServiceImpl;

import com.auth._workfoxtech.repository.EmployeeRepository;
import com.auth._workfoxtech.services.AuthServiceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceConfigImpl implements AuthServiceConfig {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return (UserDetails) employeeRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
            }
        };
    }
}
