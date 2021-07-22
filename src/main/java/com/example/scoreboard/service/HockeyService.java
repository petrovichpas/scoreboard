package com.example.scoreboard.service;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.repositories.HockeyScoreBoardRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@Data
public class HockeyService {
    private final HockeyScoreBoardRepository hockeyScoreBoardRepository;
//    private HockeyScoreBoard board;
    private Integer currentTime;
    private Integer maxTime;
    private String startStop = "Start";
    private boolean isCountdownModeSelected = false, isFirstStart = true;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    public HockeyService(HockeyScoreBoardRepository hockeyScoreBoardRepository) {
        this.hockeyScoreBoardRepository = hockeyScoreBoardRepository;
        this.currentTime = hockeyScoreBoardRepository.findById(2L).get().getCurrentTime();
        this.maxTime = hockeyScoreBoardRepository.findById(2L).get().getMaxTime();
    }





    //    public String getTextFieldTime() {
//        textFieldTime = String.format("%02d:%02d", board.getCurrentTime() / 60, board.getCurrentTime() % 60);
//        return textFieldTime;
//    }

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
       hockeyScoreBoardRepository.delete(findById(id));
    }

    @Transactional
    public void plusOrMinusOne(String param, Long id) {
        HockeyScoreBoard board = findById(id);
        Integer temp = 0;
        switch (param) {
            case "minutesPlus" : board.setCurrentTime(board.getCurrentTime() + 60);
                break;
            case "minutesMinus" : board.setCurrentTime(board.getCurrentTime() - 60);
                break;
            case "secondsPlus" : board.setCurrentTime(board.getCurrentTime() + 1);
                break;
            case "secondsMinus" : board.setCurrentTime(board.getCurrentTime() - 1);
                break;
            case "homeScorePlus" : board.setHomeScore(board.getHomeScore() + 1);
                temp = board.getHomeScore();
                break;
            case "homeScoreMinus" : board.setHomeScore(board.getHomeScore() - 1);
                break;
            case "periodPlus" : board.setPeriod(board.getPeriod() + 1);
                break;
            case "periodMinus" : board.setPeriod(board.getPeriod() - 1);
                break;
            case "awayScorePlus" : board.setAwayScore(board.getAwayScore() + 1);
                break;
            case "awayScoreMinus" : board.setAwayScore(board.getAwayScore() - 1);
                break;
        }
    }

//    Runnable forwardTask = () -> {
//        if (startStop.equals("Stop")) {
//            ++currentTime;
//            if (findById(2L).getCurrentTime() == findById(2L).getMaxTime()) {
//                findById(2L).setCurrentTime(0);
//                startStop = "Start";
//            }
//        }
//    };
//
//    Runnable countdownTask = () -> {
//        if (startStop.equals("Stop")) {
//            if (findById(2L).getCurrentTime() == 0) {
//                findById(2L).setCurrentTime(findById(2L).getMaxTime());
//                startStop = "Start";
//            } else findById(2L).setCurrentTime(findById(2L).getCurrentTime() - 1);
//        }
//    };
}
