package com.example.demo.Repositories;

import com.example.demo.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String coach);
    // Custom query methods if needed
}
