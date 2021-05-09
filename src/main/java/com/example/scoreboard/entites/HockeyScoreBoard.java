package com.example.scoreboard.entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "hhh")
@Data
@NoArgsConstructor
public class HockeyScoreBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String homeName, awayName, description;

    @Column(nullable = false, length = 2)
    private int homeScore, awayScore, minutes, seconds;

    @Column(nullable = false, length = 1)
    private int period;

}
