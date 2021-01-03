package com.boot.meal.config.security;

import com.boot.meal.security.exception.JwtAccessDeniedHandler;
import com.boot.meal.security.JwtAuthenticationEntryPoint;
import com.boot.meal.security.Role;
import com.boot.meal.security.provider.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private JwtConfigurer securityConfigurerAdapter() {
        return new JwtConfigurer(jwtAuthTokenProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/signup").permitAll()
                .antMatchers("/api/v1/login").permitAll()
                .antMatchers("/api/v1/**").hasAnyAuthority(Role.USER.getRole())
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(securityConfigurerAdapter());
    }
}
