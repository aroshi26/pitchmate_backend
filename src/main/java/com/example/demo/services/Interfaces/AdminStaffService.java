package com.example.demo.services.Interfaces;

import com.example.demo.Entities.AdminStaff;
import java.util.List;
import java.util.Optional;

public interface AdminStaffService {
    List<AdminStaff> getAllStaff();
    Optional<AdminStaff> getStaffById(Integer id);
    AdminStaff createStaff(AdminStaff staff);
    AdminStaff updateStaff(Integer id, AdminStaff staff);
    void deleteStaff(Integer id);
}
