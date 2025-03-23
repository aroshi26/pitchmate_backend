package com.example.demo.Controllers;

import com.example.demo.Entities.AdminStaff;
import com.example.demo.Repositories.AdminStaffRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminStaffRepository adminStaffRepository;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Unauthorized: No token provided");
        }

        String idToken = authHeader.substring(7);
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();

            // 🔹 Fetch the user with staff ID
            Optional<AdminStaff> adminStaffOpt = adminStaffRepository.findByEmail(email);

            if (adminStaffOpt.isPresent()) {
                AdminStaff adminStaff = adminStaffOpt.get();
                String role = adminStaff.getRole().getRoleName();
                Integer staffId = adminStaff.getStaffId(); // ✅ Get staff ID

                // ✅ Debugging
                System.out.println("✅ Retrieved Role: " + role + ", Staff ID: " + staffId);

                // 🔹 Send staffId in response
                Map<String, Object> response = new HashMap<>();
                response.put("email", email);
                response.put("role", role);
                response.put("staffId", staffId);
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(404).body("❌ User not found.");
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
        }
    }

}
