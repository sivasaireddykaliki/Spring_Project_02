package com.example.springproject2.security;

import com.example.springproject2.config.JwtAuthenticationFilter;
import com.example.springproject2.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class DemoSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        /*

            admin  = get,put
            manager= evrything
            principal = get,post
            employee = get
         */
        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/products").hasAnyRole(Role.MANAGER.name(),Role.PRINCIPAL.name(),Role.ADMIN.name(),Role.EMPLOYEE.name())
                        .requestMatchers(HttpMethod.GET,"/api/products/**").hasAnyRole(Role.MANAGER.name(),Role.PRINCIPAL.name(),Role.ADMIN.name(),Role.EMPLOYEE.name())
                        .requestMatchers(HttpMethod.POST,"/api/products").hasAnyRole(Role.MANAGER.name(),Role.PRINCIPAL.name())
                        .requestMatchers(HttpMethod.PUT,"/api/products/**").hasAnyRole(Role.MANAGER.name(),Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/products/**").hasAnyRole(Role.MANAGER.name())
                        .anyRequest().authenticated()
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        //disable csrf
        http.csrf(csrf-> csrf.disable());

        return http.build();
    }

}
