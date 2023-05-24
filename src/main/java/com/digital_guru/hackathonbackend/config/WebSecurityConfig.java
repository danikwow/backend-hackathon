package com.digital_guru.hackathonbackend.config;

import com.digital_guru.hackathonbackend.mapper.UserMapperImpl;
import com.digital_guru.hackathonbackend.security.AuthenticationManager;
import com.digital_guru.hackathonbackend.security.BearerTokenServerAuthenticationConverter;
import com.digital_guru.hackathonbackend.security.JwtHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@EnableReactiveMethodSecurity

public class WebSecurityConfig {
    @Value("${jwt.secret}")
    private String secret;
    private final String [] publicRoutes = {"/api/auth/registration", "/api/auth/login" };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity, AuthenticationManager authenticationManager) {
        return httpSecurity
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(publicRoutes).permitAll()
                .anyExchange().authenticated()
                .and()
                .formLogin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> {
                    log.error("IN securityWebFilterChain - unauthorized error: {}", e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                })
                .accessDeniedHandler((swe, e) -> {
                    log.error("IN securityWebFilterChain - access denied:{}", e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                })
                .and()
                .addFilterAt(bearerAuthenticationFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION )
                .build();
    }

    private AuthenticationWebFilter bearerAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationWebFilter bearerAuthenticationFilter = new AuthenticationWebFilter(authenticationManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(new JwtHandler(secret)));
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));

        return bearerAuthenticationFilter;
    }

    @Bean
    public UserMapperImpl userMapper(){
        return new UserMapperImpl();
    }

}
