package com.example.scoreboard.repositories;

import com.example.scoreboard.entites.HockeyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HockeyBoardRepository extends JpaRepository<HockeyBoard, Long> {
}