package com.lucas.taskmanager.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secrete;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String email) {
        SecretKey key = Keys.hmacShaKeyFor(secrete.getBytes());

        String jwt = Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expiration)).signWith(key).compact();

        return jwt;
    }
}
