package com.blm.taskme.security.provider;

import com.blm.taskme.security.DefaultUserDetails;
import com.blm.taskme.security.DefaultUserDetailsService;
import com.blm.taskme.security.JwtTokenProvider;
import com.blm.taskme.security.exception.DefaultAuthenticationException;
import com.blm.taskme.security.exception.InvalidJwtTokenException;
import com.blm.taskme.security.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final DefaultUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenAuthenticationProvider(PasswordEncoder passwordEncoder, DefaultUserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();

        String username = null;

        try {
            username = jwtTokenProvider.getUserName(token);
        } catch (InvalidJwtTokenException e) {
            throw new JwtAuthenticationException("Invalid token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        DefaultUserDetails defaultUserDetails = null;

        if ((userDetails instanceof DefaultUserDetails)) {
            defaultUserDetails = (DefaultUserDetails) userDetails;
        } else {
            throw new DefaultAuthenticationException("Not supported UserDetails");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        authenticationToken.setDetails(defaultUserDetails);

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
