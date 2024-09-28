package com.example.semiautomatedlims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())  // Modern method for authorization
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if not required
                .logout(logout -> logout  // Add logout configuration
                        .logoutUrl("/logout")  // URL to trigger logout
                        .logoutSuccessUrl("/client-login")  // Redirect to login page after logout
                        .invalidateHttpSession(true)  // Invalidate the session
                        .deleteCookies("JSESSIONID")  // Delete session cookie
                )
                .logout(logout -> logout  // Add logout configuration for staff
                        .logoutUrl("/staff-logout")  // URL to trigger logout for staff
                        .logoutSuccessUrl("/STAFF-login")  // Redirect to staff login page after logout
                        .invalidateHttpSession(true)  // Invalidate the session
                        .deleteCookies("JSESSIONID")  // Delete session cookie
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password("password")  // No hashing applied
                .roles("USER")
                .build());
        return manager;
    }

//    // Hash but normal login
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // No Hash
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();  // No password hashing
    }

    // hashed
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
}
