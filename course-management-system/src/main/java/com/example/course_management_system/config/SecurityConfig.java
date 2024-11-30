package com.example.course_management_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.course_management_system.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasAuthority("admin")
                .anyRequest().permitAll()
                // .requestMatchers("/student/**").authenticated()                   
        )
        .formLogin(form -> form
            // .loginPage("/login")
            // .loginProcessingUrl("/login")
            // .usernameParameter("username")
            // .passwordParameter("password")
            .defaultSuccessUrl("/admin", true) 
            .failureUrl("/login?error=true")
            .successHandler((request, response, authentication) -> {
                String username = authentication.getName(); 
                System.out.println("Authenticated User: " + username);
                response.sendRedirect("/admin");
            })
            .permitAll()               
        )
        .logout(config -> config
            .logoutSuccessUrl("/") 
            .permitAll()                       
        );
        return http.build();
    }

   
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
