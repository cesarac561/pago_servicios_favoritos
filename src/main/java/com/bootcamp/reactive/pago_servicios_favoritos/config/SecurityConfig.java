package com.bootcamp.reactive.pago_servicios_favoritos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http){

//        http.authorizeExchange()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();

        http.authorizeExchange()
                .pathMatchers("/favoritos").permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }
}
