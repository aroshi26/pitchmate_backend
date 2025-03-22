package com.example.demo.Controllers;

import com.example.demo.Entities.AdminStaff;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.Specialization;
import com.example.demo.Repositories.RoleRepository;
import com.example.demo.Repositories.SpecializationRepository;
import com.example.demo.services.EmailService;
import com.example.demo.services.Interfaces.AdminStaffService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin-staff")
public class AdminStaffController {

    @Autowired
    private AdminStaffService adminStaffService;
    @Autowired
    private SpecializationRepository specializationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmailService emailService; // ✅ Inject Email Service

    @PostMapping
    public ResponseEntity<?> createStaff(@RequestBody Map<String, Object> payload) {
        try {
            String name = (String) payload.get("name");
            String email = (String) payload.get("email");
            Integer roleId = Integer.parseInt(payload.get("role").toString());
            Integer specializationId = Integer.parseInt(payload.get("specialization").toString());

            // ✅ Generate a temporary password
            String tempPassword = generateRandomPassword(8);

            // ✅ Create Firebase User
            UserRecord userRecord;
            try {
                userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
                System.out.println("🔍 Firebase User Exists: " + email);
            } catch (Exception e) {
                UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                        .setEmail(email)
                        .setPassword(tempPassword)
                        .setDisplayName(name)
                        .setDisabled(false);

                userRecord = FirebaseAuth.getInstance().createUser(request);
                System.out.println("✅ Firebase User Created: " + email);
            }

            String firebaseUid = userRecord.getUid();

            // ✅ Fetch Role and Specialization from DB
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("🚨 Role not found: " + roleId));

            Specialization specialization = specializationRepository.findById(specializationId)
                    .orElseThrow(() -> new RuntimeException("🚨 Specialization not found: " + specializationId));

            // ✅ Create Staff in DB
            AdminStaff staff = new AdminStaff();
            staff.setName(name);
            staff.setEmail(email);
            staff.setFirebaseUid(firebaseUid);
            staff.setRole(role);
            staff.setSpecialization(specialization);

            AdminStaff savedStaff = adminStaffService.createStaff(staff);

            // ✅ Send email with temporary password
            emailService.sendTemporaryPassword(email, tempPassword);

            // ✅ Return Response with Temp Password
            Map<String, Object> response = new HashMap<>();
            response.put("staff", savedStaff);
            response.put("tempPassword", tempPassword);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Error: " + e.getMessage());
        }
    }

    // ✅ Generate a random password
    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$!";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @GetMapping("/coaches")
    public ResponseEntity<List<AdminStaff>> getCoaches() {
        List<AdminStaff> coaches = adminStaffService.getAllStaff().stream()
                .filter(staff -> staff.getRole().getRoleName().equals("Coach"))
                .collect(Collectors.toList());
        return ResponseEntity.ok(coaches);
    }



    @GetMapping("/specializations")
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        return ResponseEntity.ok(specializationRepository.findAll());
    }
    @GetMapping
    public ResponseEntity<List<AdminStaff>> getAllStaff() {
        return ResponseEntity.ok(adminStaffService.getAllStaff());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminStaff> getStaffById(@PathVariable Integer id) {
        return adminStaffService.getStaffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<AdminStaff> updateStaff(@PathVariable Integer id, @RequestBody AdminStaff staff) {
        return ResponseEntity.ok(adminStaffService.updateStaff(id, staff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        adminStaffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
