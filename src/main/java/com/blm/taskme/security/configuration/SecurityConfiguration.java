package com.blm.taskme.security.configuration;

import com.blm.taskme.domain.enums.PermissionName;
import com.blm.taskme.security.provider.JwtTokenAuthenticationProvider;
import com.blm.taskme.security.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationFilter authenticationFilter;
    private final JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter authenticationFilter, JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider) {
        this.authenticationFilter = authenticationFilter;
        this.jwtTokenAuthenticationProvider = jwtTokenAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/api/v1/auth").permitAll()
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api").hasAuthority(PermissionName.ALL.name())
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtTokenAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
