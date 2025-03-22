package com.example.demo.Entities;

import com.example.demo.Entities.Enum.SessionType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "training_sessions")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionType sessionType;

    @Column(nullable = false)
    private String sessionName;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    private String conductedBy;

    @Lob
    private String description;

    @Column(nullable = false)
    private LocalDateTime sessionDatetime;

    @ManyToOne
    @JoinColumn(name = "adminId", nullable = false)
    private AdminStaff admin;

    @ManyToMany
    @JoinTable(
            name = "session_players",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> players;

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getConductedBy() {
        return conductedBy;
    }

    public void setConductedBy(String conductedBy) {
        this.conductedBy = conductedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSessionDatetime() {
        return sessionDatetime;
    }

    public void setSessionDatetime(LocalDateTime sessionDatetime) {
        this.sessionDatetime = sessionDatetime;
    }

    public AdminStaff getAdmin() {
        return admin;
    }

    public void setAdmin(AdminStaff admin) {
        this.admin = admin;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
