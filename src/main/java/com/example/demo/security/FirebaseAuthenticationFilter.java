package com.example.demo.security;

import com.example.demo.Entities.AdminStaff;
import com.example.demo.Repositories.AdminStaffRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private final AdminStaffRepository adminStaffRepository;

    public FirebaseAuthenticationFilter(AdminStaffRepository adminStaffRepository) {
        this.adminStaffRepository = adminStaffRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String idToken = authHeader.substring(7);
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

                String email = decodedToken.getEmail();

                // ✅ Fetch user role from MySQL
                Optional<AdminStaff> adminOpt = adminStaffRepository.findByEmail(email);
                if (adminOpt.isPresent()) {
                    AdminStaff admin = adminOpt.get();
                    String mysqlRole = admin.getRole().getRoleName(); // ✅ Get role from MySQL

                    System.out.println("✅ Firebase Authenticated: " + email + ", Role from MySQL: " + mysqlRole);

                    // ✅ Ensure role is prefixed with "ROLE_" for Spring Security
                    String formattedRole = "ROLE_" + mysqlRole;
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(formattedRole);

                    // ✅ Log the formatted role to confirm it matches security config
                    System.out.println("✅ Assigned Authority: " + formattedRole);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("❌ User not found in MySQL: " + email);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Firebase Authentication Failed: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
