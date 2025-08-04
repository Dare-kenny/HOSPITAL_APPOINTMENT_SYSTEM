package com.example.HospitalAppointmentSystem.configurations.jwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "hYQwOFewZVQRieLc5xppAQMZOgQhNeemBDHb8kbiwqQ=";

    public String generateToken(UserDetails userDetails){ // generate the token
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles",userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*2)) // set jwt expiration to 2 hours
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody(); // get any claim requested
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token){ // validate token expiry time
        return getClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


}
