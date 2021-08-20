package com.example.scoreboard.service;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.repositories.HockeyScoreBoardRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
//@Scope("prototype")
public class HockeyService {
    private final HockeyScoreBoardRepository hockeyScoreBoardRepository;
    private HockeyScoreBoard board;

    @Autowired
    public HockeyService(HockeyScoreBoardRepository hockeyScoreBoardRepository) {
        this.hockeyScoreBoardRepository = hockeyScoreBoardRepository;
    }

    public List<HockeyScoreBoard> findAll() {
        return hockeyScoreBoardRepository.findAll();
    }

    public HockeyScoreBoard findById(Long id) {
        return hockeyScoreBoardRepository.findById(id).get();
    }

    public HockeyScoreBoard save(HockeyScoreBoard hockeyScoreBoard) {
        return hockeyScoreBoardRepository.save(hockeyScoreBoard);
    }

    public void deleteById(Long id) {
       hockeyScoreBoardRepository.deleteById(id);
    }

    public Integer plusOrMinusOne(String param, Long id) {
        board = findById(id);
        switch (param) {
            case "minutesPlus" : board.setCurrentTime(board.getCurrentTime() + 60);
                return board.getCurrentTime();
            case "minutesMinus" : board.setCurrentTime(board.getCurrentTime() >= 60 ? board.getCurrentTime() - 60 : board.getCurrentTime());
                return board.getCurrentTime();
            case "secondsPlus" : board.setCurrentTime(board.getCurrentTime() + 1);
                return board.getCurrentTime();
            case "secondsMinus" : board.setCurrentTime(board.getCurrentTime() > 0 ? board.getCurrentTime() - 1 : board.getCurrentTime());
                return board.getCurrentTime();
            case "homeScorePlus" : board.setHomeScore(board.getHomeScore() + 1);
                return board.getHomeScore();
            case "homeScoreMinus" : board.setHomeScore(board.getHomeScore() > 0 ? board.getHomeScore() - 1 : 0);
                return board.getHomeScore();
            case "periodPlus" : board.setPeriod(board.getPeriod() + 1);
                return board.getPeriod();
            case "periodMinus" : board.setPeriod(board.getPeriod() > 0 ? board.getPeriod() - 1 : 0);
                return board.getPeriod();
            case "awayScorePlus" : board.setAwayScore(board.getAwayScore() + 1);
                return board.getAwayScore();
            case "awayScoreMinus" : board.setAwayScore(board.getAwayScore() > 0 ? board.getAwayScore() - 1 : 0);
                return board.getAwayScore();
            default: return -1;
        }
    }


    public String start(Long id) {
        board = findById(id);

        if (board.getStartStop().equals("Start")) {
           board.setStartStop("Stop");

            Thread timeThread = new Thread(() -> {
                while (board.getStartStop().equals("Stop")){
                    try {
                        Thread.sleep(1000);

                        if (board.isCountdownModeSelected()){
                            if (board.getCurrentTime() <= 0) {
                                board.setCurrentTime(board.getMaxTime());
                                board.setStartStop("Start");
                            } else board.setCurrentTime(board.getCurrentTime() - 1);
                        }
                        else {
                            if (board.getCurrentTime() >= board.getMaxTime()) {
                                board.setCurrentTime(0);
                                board.setStartStop("Start");
                            } else board.setCurrentTime(board.getCurrentTime() + 1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    save(board);
                }
            });
            timeThread.setDaemon(true);
            timeThread.start();
        } else {
            board.setStartStop("Start");
            save(board);
        }

        return board.getStartStop();
    }
}
