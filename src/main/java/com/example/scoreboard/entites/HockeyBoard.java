package com.example.scoreboard.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "boards")
@NoArgsConstructor
public class HockeyBoard {

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

    @Column(nullable = false)
    private String period;

    @Column(name = "current_t")
    private Integer currentTime;

    @Transient
    private Integer penaltyTime;

    @Column(name = "max_t")
    private Integer maxTime;

    @Column(nullable = false)
    private boolean isCountdownModeSelected;

    @Column(nullable = false)
    private String startStop;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public HockeyBoard(User user) {
        this.user = user;
        currentTime = 0;
        maxTime = 1200;
        homeScore = 0;
        awayScore = 0;
        period = "1";
        isCountdownModeSelected = false;
        startStop = "Start";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HockeyBoard that = (HockeyBoard) o;
        return id == that.id && Objects.equals(homeName, that.homeName) && Objects.equals(awayName, that.awayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, homeName, awayName);
    }
}
