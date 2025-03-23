package com.example.demo.Controllers;

import com.example.demo.Entities.PlayerRole;
import com.example.demo.Repositories.PlayerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/player-roles")
public class PlayerRoleController {

    @Autowired
    private PlayerRoleRepository playerRoleRepository;

    @GetMapping
    public ResponseEntity<List<PlayerRole>> getAllRoles() {
        return ResponseEntity.ok(playerRoleRepository.findAll());
    }
}
