package com.example.demo.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false) // ✅ Who is giving the feedback (Coach/Medical)
    private AdminStaff givenBy;

    @Column(nullable = false, length = 1000)
    private String feedback;

    @Column(nullable = false)
    private LocalDateTime timestamp;  // ✅ Automatically stores the time when feedback is given

    // ✅ Constructors
    public Feedback() {
        this.timestamp = LocalDateTime.now(); // Set timestamp when feedback is created
    }

    public Feedback(Player player, AdminStaff givenBy, String feedback) {
        this.player = player;
        this.givenBy = givenBy;
        this.feedback = feedback;
        this.timestamp = LocalDateTime.now(); // Set timestamp
    }

    // ✅ Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public AdminStaff getGivenBy() {
        return givenBy;
    }

    public void setGivenBy(AdminStaff givenBy) {
        this.givenBy = givenBy;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
