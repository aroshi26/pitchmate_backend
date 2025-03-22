package com.example.demo.Repositories;

import com.example.demo.Entities.AdminStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminStaffRepository extends JpaRepository<AdminStaff, Integer> {

    /**
     * Find an AdminStaff entity by email.
     *
     * @param email the email to search for
     * @return an Optional containing the AdminStaff if found, or empty if not
     */
    Optional<AdminStaff> findByEmail(String email);
}
