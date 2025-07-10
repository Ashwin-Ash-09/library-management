package com.racoonash.librarymanagement.librarymanagement.Configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // This configuration class sets up security for the application.
    // It disables form login, allows public access to the "/api/hello" endpoint,
    // restricts access to "/api/users/**" to users with the "ADMIN" role,
    // and requires either "ADMIN" or "LIBRARIAN" roles for all other endpoints.
    // It also configures HTTP Basic authentication and uses an in-memory user store
    // with two users: "admin" with the "ADMIN" role and "librarian" with the "LIBRARIAN" role.
    // Passwords are encoded using BCrypt
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .formLogin(login -> login.disable())

                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/hello").permitAll()
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .anyRequest().hasAnyRole("ADMIN", "LIBRARIAN"))

                .csrf(cors -> cors.disable())
                .httpBasic(withDefaults());

        return http.build();

    }


    @Bean
    public InMemoryUserDetailsManager users(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails librarian = User.builder()
                .username("libr")
                .password(passwordEncoder.encode("admin"))
                .roles("LIBRARIAN")
                .build();
        return new InMemoryUserDetailsManager(admin, librarian);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
