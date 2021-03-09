package com.blm.taskme.security;

import com.blm.taskme.security.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expirationTimeInMilliseconds;

    public String createToken(String username) {
        Date expirationDate = new Date(new Date().getTime() + expirationTimeInMilliseconds);

        Claims claims = Jwts.
                claims().
                setSubject(username).
                setExpiration(expirationDate).
                setIssuedAt(new Date());
        return Jwts.
                builder().
                setClaims(claims).
                signWith(SignatureAlgorithm.HS256, secret).
                compact();
    }

    public boolean isValid(String token) {
        return Jwts.
                parser().
                setSigningKey(secret).
                parseClaimsJws(token).
                getBody().
                getExpiration().
                before(new Date());
    }

    public String getUserName(String token) throws InvalidJwtTokenException {
        try {
            return Jwts.
                    parser().
                    setSigningKey(secret).
                    parseClaimsJws(token).
                    getBody().getSubject();
        } catch (Exception e) {
            throw new InvalidJwtTokenException();
        }
    }
}