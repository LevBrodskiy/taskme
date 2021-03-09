package com.blm.taskme.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Lazy
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.authorizationHeader}")
    private String authorizationHeader;

    @Autowired
    private AuthenticationManager manager;

    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public JwtAuthenticationFilter(
            @Qualifier("jwtTokenAuthenticationProvider") AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(authorizationHeader);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(token, null);

        try {
            Authentication user = manager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(user);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
