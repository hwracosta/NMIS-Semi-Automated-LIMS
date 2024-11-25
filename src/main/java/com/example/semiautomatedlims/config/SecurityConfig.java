package com.example.semiautomatedlims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()  // Allow all requests; access control is handled in controllers
                )
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF if not required
                .headers(headers -> headers
                        .cacheControl(cache -> cache.disable())  // Disable caching for all pages
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // General logout URL for client
                        .logoutSuccessUrl("/client-login") // Redirect to client login page after logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete session cookies
                )
                .logout(logout -> logout
                        .logoutUrl("/staff-testing-logout") // Logout URL for staff-testing
                        .logoutSuccessUrl("/STAFF-login") // Redirect to the common staff login page
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete session cookies
                )
                .logout(logout -> logout
                        .logoutUrl("/staff-releasing-logout") // Logout URL for staff-releasing
                        .logoutSuccessUrl("/STAFF-login") // Redirect to the common staff login page
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete session cookies
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
