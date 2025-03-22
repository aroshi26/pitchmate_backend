package com.example.demo.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PlayerPerformance")
public class PlayerPerformance {

    @EmbeddedId
    private PlayerPerformanceId id;
    // 🏏 Batting Statistics
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer innings = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer runsScored = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer ballsFaced = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer hundreds = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer fifties = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer fours = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer sixes = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer ducks = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer dismissalsInvolved = 0;

    // 🎯 Bowling Statistics
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer oversBowled = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer ballsBowled = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer runsConceded = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer wicketsTaken = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer fiveWicketHauls = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer maidens = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer dotBalls = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer hattricks = 0;

    // 🏏 Fielding Statistics
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer catches = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer runOuts = 0;
    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer stumpings = 0;


    public PlayerPerformance() {
        this.id = new PlayerPerformanceId(); // Avoids null issues
    }

    // ✅ Constructor with ID
    public PlayerPerformance(PlayerPerformanceId id) {
        this.id = id;
    }
    public PlayerPerformanceId getId() { return id; }
    public void setId(PlayerPerformanceId id) { this.id = id; }

    public Integer getInnings() { return innings; }
    public void setInnings(Integer innings) { this.innings = innings; }

    public Integer getRunsScored() { return runsScored; }
    public void setRunsScored(Integer runsScored) { this.runsScored = runsScored; }

    public Integer getBallsFaced() { return ballsFaced; }
    public void setBallsFaced(Integer ballsFaced) { this.ballsFaced = ballsFaced; }

    public Integer getHundreds() { return hundreds; }
    public void setHundreds(Integer hundreds) { this.hundreds = hundreds; }

    public Integer getFifties() { return fifties; }
    public void setFifties(Integer fifties) { this.fifties = fifties; }

    public Integer getFours() { return fours; }
    public void setFours(Integer fours) { this.fours = fours; }

    public Integer getSixes() { return sixes; }
    public void setSixes(Integer sixes) { this.sixes = sixes; }

    public Integer getDucks() { return ducks; }
    public void setDucks(Integer ducks) { this.ducks = ducks; }

    public Integer getDismissalsInvolved() { return dismissalsInvolved; }
    public void setDismissalsInvolved(Integer dismissalsInvolved) { this.dismissalsInvolved = dismissalsInvolved; }

    public Integer getOversBowled() { return oversBowled; }
    public void setOversBowled(Integer oversBowled) { this.oversBowled = oversBowled; }

    public Integer getBallsBowled() { return ballsBowled; }
    public void setBallsBowled(Integer ballsBowled) { this.ballsBowled = ballsBowled; }

    public Integer getRunsConceded() { return runsConceded; }
    public void setRunsConceded(Integer runsConceded) { this.runsConceded = runsConceded; }

    public Integer getWicketsTaken() { return wicketsTaken; }
    public void setWicketsTaken(Integer wicketsTaken) { this.wicketsTaken = wicketsTaken; }

    public Integer getFiveWicketHauls() { return fiveWicketHauls; }
    public void setFiveWicketHauls(Integer fiveWicketHauls) { this.fiveWicketHauls = fiveWicketHauls; }

    public Integer getMaidens() { return maidens; }
    public void setMaidens(Integer maidens) { this.maidens = maidens; }

    public Integer getDotBalls() { return dotBalls; }
    public void setDotBalls(Integer dotBalls) { this.dotBalls = dotBalls; }

    public Integer getHattricks() { return hattricks; }
    public void setHattricks(Integer hattricks) { this.hattricks = hattricks; }

    public Integer getCatches() { return catches; }
    public void setCatches(Integer catches) { this.catches = catches; }

    public Integer getRunOuts() { return runOuts; }
    public void setRunOuts(Integer runOuts) { this.runOuts = runOuts; }

    public Integer getStumpings() { return stumpings; }
    public void setStumpings(Integer stumpings) { this.stumpings = stumpings; }
}
