package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.JwtAuthenticationRequest;
import com.blm.taskme.api.v1.response.AuthenticationToken;
import com.blm.taskme.security.JwtTokenProvider;
import com.blm.taskme.security.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultJwtAuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public DefaultJwtAuthenticationService(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public AuthenticationToken getAuthenticationToken(JwtAuthenticationRequest request) throws AuthenticationException {
        UserDetails details = userDetailsService.loadUserByUsername(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), details.getPassword())) {
            throw new JwtAuthenticationException("Bad credentials");
        }

        String token = jwtTokenProvider.createToken(details.getUsername());

        return new AuthenticationToken(token);
    }
}
