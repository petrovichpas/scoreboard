package com.example.scoreboard.service;

import com.example.scoreboard.entites.HockeyBoard;
import com.example.scoreboard.repositories.HockeyBoardRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Data
public class HockeyService {
    private final HockeyBoardRepository hockeyScoreBoardRepository;
    private Set<HockeyBoard> boardSet;

    @Autowired
    public HockeyService(HockeyBoardRepository hockeyScoreBoardRepository) {
        this.hockeyScoreBoardRepository = hockeyScoreBoardRepository;
        boardSet = new LinkedHashSet();
        boardSet.addAll(findAll());
    }

    public List<HockeyBoard> findAll() {
        return hockeyScoreBoardRepository.findAll();
    }

    public HockeyBoard findById(Long id) {
        return boardSet.stream().filter(e-> e.getId().equals(id)).findFirst().get();
//        return hockeyScoreBoardRepository.findById(id).get();
    }

    public HockeyBoard save(HockeyBoard board) {
        boardSet.add(hockeyScoreBoardRepository.save(board));
        return boardSet.stream().skip(boardSet.size()-1).findFirst().get();
//        return hockeyScoreBoardRepository.save(hockeyScoreBoard);
    }

    public void deleteById(Long id) {
       hockeyScoreBoardRepository.deleteById(id);
    }

    public String plusOrMinusOne(String param, Long id) {
        HockeyBoard board = findById(id);
        String response = "No matches";
        switch (param) {
            case "minutesPlus":
                board.setCurrentTime(board.getCurrentTime() + 60);
                response = "currentTime-" + timeToString(board.getCurrentTime());
                break;
            case "minutesMinus":
                board.setCurrentTime(board.getCurrentTime() >= 60 ? board.getCurrentTime() - 60 : board.getCurrentTime());
                response = "currentTime-" + timeToString(board.getCurrentTime());
                break;
            case "secondsPlus":
                board.setCurrentTime(board.getCurrentTime() + 1);
                response = "currentTime-" + timeToString(board.getCurrentTime());
                break;
            case "secondsMinus":
                board.setCurrentTime(board.getCurrentTime() > 0 ? board.getCurrentTime() - 1 : board.getCurrentTime());
                response = "currentTime-" + timeToString(board.getCurrentTime());
                break;
            case "homeScorePlus":
                board.setHomeScore(board.getHomeScore() + 1);
                response = "homeScore-" + board.getHomeScore();
                break;
            case "homeScoreMinus":
                board.setHomeScore(board.getHomeScore() > 0 ? board.getHomeScore() - 1 : 0);
                response = "homeScore-" + board.getHomeScore();
                break;
            case "periodPlus":
                if (board.getPeriod().equals("OT")) board.setPeriod("1");
                else board.setPeriod(String.valueOf(Integer.parseInt(board.getPeriod()) + 1));
                response = "period-" + board.getPeriod();
                break;
            case "periodMinus":
                if (board.getPeriod().equals("OT"));
                else if (Integer.parseInt(board.getPeriod()) > 1) board.setPeriod(String.valueOf(Integer.parseInt(board.getPeriod()) - 1));
                else if (Integer.parseInt(board.getPeriod()) <= 1) board.setPeriod("OT");
                response = "period-" + board.getPeriod();
                break;
            case "awayScorePlus":
                board.setAwayScore(board.getAwayScore() + 1);
                response = "awayScore-" + board.getAwayScore();
                break;
            case "awayScoreMinus":
                board.setAwayScore(board.getAwayScore() > 0 ? board.getAwayScore() - 1 : 0);
                response = "awayScore-" + board.getAwayScore();
                break;
        }
        save(board);
        return response;
    }

    public String start(Long id) {
        HockeyBoard board = findById(id);

        if (board.getStartStop().equals("Start")) {
            board.setStartStop("Stop");
            Thread timeThread = new Thread(() -> {
                while (board.getStartStop().equals("Stop")){
                    try {
                        Thread.sleep(999);

                        for (Map.Entry<String, Integer> p : board.getPenalty().entrySet()) {
                            if (p.getValue() > 0) p.setValue(p.getValue() - 1);
                            else board.getPenalty().remove(p.getKey());
                        }

                        if (board.isCountdownModeSelected()){
                            if (board.getCurrentTime() <= 0) {
                                board.setCurrentTime(board.getMaxTime());
                                board.setStartStop("Start");
                            } else board.setCurrentTime(board.getCurrentTime() - 1);
                        } else {
                            if (board.getCurrentTime() >= board.getMaxTime()) {
                                board.setCurrentTime(0);
                                board.setStartStop("Start");
                            } else board.setCurrentTime(board.getCurrentTime() + 1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            timeThread.setDaemon(true);
            timeThread.start();
        } else board.setStartStop("Start");
        save(board);
        return board.getStartStop();
    }

    public String timeToString(Integer seconds){
        if (seconds > 0)
            return String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60);
        else
            return "00:00";
    }

    public Integer timeToSeconds(String time){
        return Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3, 5));
    }
}
