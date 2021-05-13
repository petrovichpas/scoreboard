package com.example.scoreboard.entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "hockeySB")
@Data
@NoArgsConstructor
public class HockeyScoreBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String homeName, awayName, description;

    @Column(nullable = false, length = 2)
    private int minutes, seconds;

//    public HockeyScoreBoard(String homeName, String awayName, String description, int minutes) {
//        this.homeName = homeName;
//        this.awayName = awayName;
//        this.description = description;
//        this.minutes = minutes;
//        this.seconds = 0;
//    }
}
