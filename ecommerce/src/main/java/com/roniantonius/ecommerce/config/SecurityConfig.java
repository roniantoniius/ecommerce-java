package com.roniantonius.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/v1/produks/hapus-produk")
	                .authenticated() // Hanya endpoint ini yang butuh autentikasi
	            .anyRequest().permitAll() // Selain yang di-list, semuanya boleh diakses
	        )
	        .csrf().disable()
	        .formLogin().disable()
	        .httpBasic().disable();

	    return http.build();
	}
}
