package com.example.course_management_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.course_management_system.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/login", "/register").permitAll()  // Allow unauthenticated access to login/register
                .requestMatchers("/admin/**").hasRole("admin")      // Admin pages require ADMIN role
                .anyRequest().authenticated()
                // .requestMatchers("/student/**").authenticated()                   
        )
        .formLogin(login -> login
            .loginPage("/login")        // Custom login page
            .loginProcessingUrl("/login") // POST request URL for login
            .defaultSuccessUrl("/admin", true) // Redirect after successful login
            .failureUrl("/login?error=true")
            // .permitAll()               // Allow everyone to access the login page
        // )
        // .logout(logout -> logout
        //     .logoutSuccessUrl("/login?logout") // Redirect to login page on logout
        //     .permitAll()                       // Allow everyone to logout
        );
        return http.build();
    }
}
