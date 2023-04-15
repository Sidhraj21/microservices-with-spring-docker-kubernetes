package com.udaipur.gatewayserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

//	previous method without Roles Authorization
//	@Bean
//	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//		http.authorizeExchange(exchanges -> exchanges.pathMatchers("/mybank/accounts/**").authenticated().pathMatchers("/mybank/loans/**").authenticated().pathMatchers("/mybank/cards/**").permitAll()).oauth2ResourceServer().jwt();
//		http.csrf().disable();
//		return http.build();
//	}
	
	// when we want to authorize a request which has ACCOUNTS roles granted for it in keycloak
	// clients...
	
	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchanges -> exchanges.pathMatchers("/mybank/accounts/**").hasRole("ACCOUNTS")
                        .pathMatchers("/mybank/cards/**").authenticated()
                        .pathMatchers("/mybank/loans/**").permitAll())
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(grantedAuthoritiesExtractor());
        http.csrf().disable();
        return http.build();
    }

    Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
