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

////  ----------homePenalty---------------
//    @Column
//    private String homePenaltyNumber1;
//    private String homePenaltyNumber2;
//    private String homePenaltyNumber3;
//
//    @Column(length = 1)
//    private int homePenaltyMinutes1;
//    private int homePenaltyMinutes2;
//    private int homePenaltyMinutes3;
//
//    @Column(length = 2)
//    private int homePenaltySeconds1;
//    private int homePenaltySeconds2;
//    private int homePenaltySeconds3;
//
////  ----------awayPenalty---------------
//    @Column
//    private String awayPenaltyNumber1;
//    private String awayPenaltyNumber2;
//    private String awayPenaltyNumber3;
//
//    @Column(length = 1)
//    private int awayPenaltyMinutes1;
//    private int awayPenaltyMinutes2;
//    private int awayPenaltyMinutes3;
//
//    @Column(length = 2)
//    private int awayPenaltySeconds1;
//    private int awayPenaltySeconds2;
//    private int awayPenaltySeconds3;
}
