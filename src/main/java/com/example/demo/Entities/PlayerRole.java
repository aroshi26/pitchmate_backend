package com.example.demo.Entities;

import com.example.demo.Entities.Enum.PlayerRoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "PlayerRoles")
public class PlayerRole {

    @Id
    private Integer playerRoleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerRoleType roleName;

    public Integer getPlayerRoleId() {
        return playerRoleId;
    }

    public void setPlayerRoleId(Integer playerRoleId) {
        this.playerRoleId = playerRoleId;
    }

    public PlayerRoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(PlayerRoleType roleName) {
        this.roleName = roleName;
    }

}
