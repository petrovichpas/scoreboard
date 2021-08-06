package com.example.scoreboard.service;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.repositories.HockeyScoreBoardRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@Data
public class HockeyService {
    private final HockeyScoreBoardRepository hockeyScoreBoardRepository;
//    private Integer currentTime;
//    private Integer maxTime;
    private String startStop = "Start";
    private boolean isCountdownModeSelected = false, isFirstStart = true;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);


    public List<HockeyScoreBoard> findAll() {
        return hockeyScoreBoardRepository.findAll();
    }

    public HockeyScoreBoard findById(Long id) {
        HockeyScoreBoard board = hockeyScoreBoardRepository.findById(id).get();
        return board;
    }

    public HockeyScoreBoard save(HockeyScoreBoard hockeyScoreBoard) {
        return hockeyScoreBoardRepository.save(hockeyScoreBoard);
    }

    public void deleteById(Long id) {
       hockeyScoreBoardRepository.delete(findById(id));
    }

    public Integer plusOrMinusOne(String param, Long id) {
        HockeyScoreBoard board = findById(id);
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


    public void start(Long id) {
        new Thread(() -> {
            while (startStop.equals("Stop")){
                try {
                    HockeyScoreBoard board = findById(id);
                    Thread.sleep(1000);
                    if (isCountdownModeSelected){
                        if (board.getCurrentTime() <= 0) {
                            board.setCurrentTime(board.getMaxTime());
                            setStartStop("Start");
                        } else board.setCurrentTime(board.getCurrentTime() - 1);
                    }
                    else {
                        if (board.getCurrentTime() >= board.getMaxTime()) {
                            board.setCurrentTime(0);
                            setStartStop("Start");
                        } else board.setCurrentTime(board.getCurrentTime() + 1);
                    }
                    save(board);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//       return scheduledExecutorService.scheduleAtFixedRate(() -> {
//            HockeyScoreBoard board = findById(id);
//
//            if (startStop.equals("Stop")) {
//                if (isCountdownModeSelected){
//                    if (board.getCurrentTime() <= 0) {
//                        board.setCurrentTime(board.getMaxTime());
//                        setStartStop("Start");
//                    } else board.setCurrentTime(board.getCurrentTime() - 1);
//                }
//                else {
//                    if (board.getCurrentTime() >= board.getMaxTime()) {
//                        board.setCurrentTime(0);
//                        setStartStop("Start");
//                    } else board.setCurrentTime(board.getCurrentTime() + 1);
//                }
//            }
//            save(board);
//        } ,1,1, TimeUnit.SECONDS);
    }
}
