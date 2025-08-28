package com.auth._workfoxtech.common;

import com.auth._workfoxtech.services.AuthServiceConfig;
import com.auth._workfoxtech.services.EmployeeService;
import com.auth._workfoxtech.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilterAuthentication extends OncePerRequestFilter {

    private final JwtService jwtService;



    private final AuthServiceConfig authServiceConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String Jwt;
        final String userEmail;
        if(StringUtils.isEmpty(authHeader) ){
            filterChain.doFilter(request,response);
            return;
        }
        Jwt=authHeader.substring(7);
        userEmail=jwtService.extractUsername(Jwt);
        if(!StringUtils.isEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()== null){
            UserDetails userDetails=authServiceConfig.userDetailsService().loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(Jwt,userDetails)){
                SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request,response);
    }
}
