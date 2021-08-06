package com.example.scoreboard.entites;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "hockey")
public class HockeyScoreBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_name")
    private String homeName;

    @Column(name = "away_name")
    private String awayName;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_score")
    private Integer awayScore;

    @Column(name = "current_t")
    private Integer currentTime;

    @Column(name = "max_t")
    private Integer maxTime;

    @Column
    private Integer period;

    public HockeyScoreBoard() {
//        this.homeName = homeName;
//        this.awayName = awayName;
//        this.maxTime = maxTime;
        currentTime = 0;
        maxTime = 1200;
        homeScore = 0;
        awayScore = 0;
        period = 1;
    }
}
