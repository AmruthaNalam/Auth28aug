package com.auth._workfoxtech.services.ServiceImpl;

import com.auth._workfoxtech.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExp;

    @Override
    public String generateToken(UserDetails userDetails){
        long now=System.currentTimeMillis();
        Date issuedAt=new Date(now);
        Date date=new Date(now + jwtExp);
        System.out.println(issuedAt +" "+ date);
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(issuedAt)
                .setExpiration(date).signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    @Override
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    private Key getSignKey(){
        byte[] key= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired at: {}"+ e.getClaims().getExpiration());
            throw e;
        } catch (Exception e) {
            System.out.println("Token parsing failed: {}"+ e.getMessage());
            throw e;
        }
    }


    @Override
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username).equals(userDetails.getUsername()) && !isTokenExperied(token);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        long now=System.currentTimeMillis();
        Date issuedAt=new Date(now);
        Date expiraryDate=new Date(now + jwtExp);
        System.out.println(issuedAt +" "+ expiraryDate+ " "+ jwtExp);
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(issuedAt)
                .setExpiration(expiraryDate).signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExperied(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

}
