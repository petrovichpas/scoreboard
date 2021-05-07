package com.example.scoreboard.repositories;

import com.example.scoreboard.entites.HockeyScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HockeyScoreBoardRepository extends JpaRepository<HockeyScoreBoard, Long> {
}