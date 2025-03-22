package com.example.demo.Repositories;

import com.example.demo.Entities.Enum.PlayerRoleType;
import com.example.demo.Entities.Player;
import com.example.demo.Entities.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRoleRepository extends JpaRepository<PlayerRole, Integer> {

        // ✅ Fetch player roles by role name
        @Query("SELECT pr FROM PlayerRole pr WHERE pr.roleName = :roleName")
        List<PlayerRole> findPlayerRolesByRoleName(@Param("roleName") PlayerRoleType roleType);

}
