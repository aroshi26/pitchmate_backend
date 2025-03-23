package com.example.demo.security;

import com.example.demo.Repositories.AdminStaffRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${front_end_url}")
    private String front_end_url;

    private final AdminStaffRepository adminStaffRepository;

    public SecurityConfig(AdminStaffRepository adminStaffRepository) {
        this.adminStaffRepository = adminStaffRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for APIs (useful for token-based auth)
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin(front_end_url); // Ensure frontend origin is added
                    config.addAllowedMethod("*");  // Allow all HTTP methods
                    config.addAllowedHeader("*");  // Allow all headers
                    return config;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Use stateless sessions for JWT
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()  // Authentication endpoints are open to all
                        .requestMatchers("/public/**").permitAll()  // Public resources

                        // Admin Permissions
                        .requestMatchers("/api/admin-staff/**").hasAuthority("ROLE_Admin")

                        // Coaches & Admins for player data
                        .requestMatchers("/api/players/**").hasAnyAuthority("ROLE_Admin", "ROLE_Coach", "ROLE_MedicalOfficer")
                        .requestMatchers(HttpMethod.POST, "/api/matches/**").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.GET, "/api/matches/**").permitAll()  // Matches can be publicly accessible

                        // Coach-only routes
                        .requestMatchers(HttpMethod.POST, "/api/teams/**").hasAuthority("ROLE_Coach")
                        .requestMatchers(HttpMethod.PUT, "/api/teams/**").hasAuthority("ROLE_Coach")
                        .requestMatchers(HttpMethod.DELETE, "/api/teams/**").hasAuthority("ROLE_Coach")
                        .requestMatchers(HttpMethod.GET, "/api/teams/**").permitAll()

                        // Allow feedback and achievements for Admin & Coach
                        .requestMatchers(HttpMethod.POST, "/api/feedback").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.GET, "/api/feedback").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.POST, "/api/player-performance/**").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.PUT, "/api/player-performance/**").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.GET, "/api/player-performance/**").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.POST, "/api/achievements").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.GET, "/api/achievements").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")
                        .requestMatchers(HttpMethod.PUT, "/api/achievements/**").hasAnyAuthority("ROLE_Admin", "ROLE_Coach")

                        // Ensure authenticated routes
                        .requestMatchers("/api/player-performance/**").authenticated()
                        .requestMatchers("/api/teams/**").authenticated()
                        .requestMatchers("/api/feedback").authenticated()
                        .requestMatchers("/api/achievements/**").authenticated()
                        .requestMatchers("/api/players/**").authenticated()

                        // Any other requests require authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new FirebaseAuthenticationFilter(adminStaffRepository), UsernamePasswordAuthenticationFilter.class); // Add Firebase Auth Filter
        return http.build();
    }
}
