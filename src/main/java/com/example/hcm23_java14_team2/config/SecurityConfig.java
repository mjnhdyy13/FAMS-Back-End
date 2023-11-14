package com.example.hcm23_java14_team2.config;

import com.example.hcm23_java14_team2.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable())
                .authorizeHttpRequests()
                .requestMatchers("/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/syllabus/**").hasAnyAuthority("VIEW_SYLLABUS", "FULLACCESS_SYLLABUS")
                .requestMatchers(HttpMethod.POST, "/api/v1/syllabus/**").hasAnyAuthority("CREATE_SYLLABUS", "FULLACCESS_SYLLABUS")
                .requestMatchers(HttpMethod.PUT, "/api/v1/syllabus/**").hasAnyAuthority("MODIFY_SYLLABUS", "FULLACCESS_SYLLABUS")
                .requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAnyAuthority("VIEW_USER", "FULLACCESS_USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasAnyAuthority("CREATE_USER", "FULLACCESS_USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAnyAuthority("MODIFY_USER", "FULLACCESS_USER")
                .requestMatchers(HttpMethod.GET, "/api/v1/trainingProgram/**").hasAnyAuthority("VIEW_TRAINING", "FULLACCESS_TRAINING")
                .requestMatchers(HttpMethod.POST, "/api/v1/trainingProgram/**").hasAnyAuthority("CREATE_TRAINING", "FULLACCESS_TRAINING")
                .requestMatchers(HttpMethod.PUT, "/api/v1/trainingProgram/**").hasAnyAuthority("MODIFY_TRAINING", "FULLACCESS_TRAINING")
                .requestMatchers("/api/v1/user-permission/**").authenticated()
                .requestMatchers("/api/v1/syllabus/**").authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .httpBasic(withDefaults())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(authEntryPoint))
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
