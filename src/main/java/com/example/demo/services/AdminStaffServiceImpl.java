package com.example.demo.services;

import com.example.demo.Entities.AdminStaff;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Repositories.AdminStaffRepository;
import com.example.demo.services.Interfaces.AdminStaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminStaffServiceImpl implements AdminStaffService {

    private final AdminStaffRepository adminStaffRepository;

    // Constructor injection is preferred for better testability.
    public AdminStaffServiceImpl(AdminStaffRepository adminStaffRepository) {
        this.adminStaffRepository = adminStaffRepository;
    }

    @Override
    public List<AdminStaff> getAllStaff() {
        return adminStaffRepository.findAll();
    }

    @Override
    public Optional<AdminStaff> getStaffById(Integer id) {
        return adminStaffRepository.findById(id);
    }

    @Override
    public AdminStaff createStaff(AdminStaff staff) {
        return adminStaffRepository.save(staff);
    }

    @Override
    public AdminStaff updateStaff(Integer id, AdminStaff staff) {
        return adminStaffRepository.findById(id).map(existing -> {
            existing.setName(staff.getName());
            existing.setEmail(staff.getEmail());
            existing.setRole(staff.getRole());
            existing.setSpecialization(staff.getSpecialization());
            return adminStaffRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Admin staff not found with id: " + id));
    }

    @Override
    public void deleteStaff(Integer id) {
        if (!adminStaffRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin staff not found with id: " + id);
        }
        adminStaffRepository.deleteById(id);
    }
}
