package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. Configure the SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for testing; enable it in production
            .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only paths
                .requestMatchers("/employee/**").hasRole("EMPLOYEE") // Employee-only paths
                .requestMatchers("/public/**", "/login").permitAll() // Publicly accessible paths
                .anyRequest().authenticated() // All other requests require authentication
            .and()
            .formLogin() // Enable form-based login
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/home") // Redirect after successful login
                .permitAll()
            .and()
            .logout() // Enable logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
        return http.build();
    }

    // 2. Define the password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Configure in-memory authentication
    @Bean
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .and()
            .withUser("employee")
            .password(passwordEncoder().encode("employee123"))
            .roles("EMPLOYEE");
    }
}
