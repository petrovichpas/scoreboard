package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.repositories.HockeyScoreBoardRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.DispatcherServlet;

import javax.management.timer.Timer;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/hockey")
public class HockeyController {

    private HockeyScoreBoardRepository hockeyScoreBoardRepository;

    @Autowired
    public HockeyController(HockeyScoreBoardRepository hockeyScoreBoardRepository) {
        this.hockeyScoreBoardRepository = hockeyScoreBoardRepository;
    }

    @Getter
    @Setter
    private boolean countdown = false;

    @Getter
    @Setter
    private int homeScore, awayScore, period, minutes, seconds, homePenaltyMinutes1, homePenaltyMinutes2, homePenaltyMinutes3, awayPenaltyMinutes1, awayPenaltyMinutes2,
            awayPenaltyMinutes3, homePenaltySeconds1, homePenaltySeconds2, homePenaltySeconds3, awayPenaltySeconds1, awayPenaltySeconds2, awayPenaltySeconds3;

    @Getter
    @Setter
    private String homePenaltyNumber1, homePenaltyNumber2, homePenaltyNumber3, awayPenaltyNumber1, awayPenaltyNumber2, awayPenaltyNumber3;



    @GetMapping("/main")
    public String main() {
        return "index";
    }

    @GetMapping("/boards")
    public String showBoards(Model model) {
        model.addAttribute("boards", hockeyScoreBoardRepository.findAll());
        return "my_boards";
    }

    @GetMapping("/create")
    public String createBoard(Model model) {
        model.addAttribute("board", new HockeyScoreBoard());
        return "board_form";
    }

    @GetMapping("/board/{id}")
    public String openBoard(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyScoreBoardRepository.findById(id).get());
        model.addAttribute("minutes", minutes);
        model.addAttribute("seconds", seconds);
        model.addAttribute("homeScore", homeScore);
        model.addAttribute("period", period);
        model.addAttribute("awayScore", awayScore);
        model.addAttribute("ss", ss);
        return "admin_board";
    }

    @GetMapping("/edit/{id}")
    public String editBoard(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyScoreBoardRepository.findById(id).get());
        return "board_form";
    }

    @PostMapping("/save")
    public String saveOrUpdateBoard(@ModelAttribute HockeyScoreBoard board) {
        hockeyScoreBoardRepository.save(board);
        return "redirect:/hockey/boards";
    }

    @PostMapping("/delete")
    public String deleteBoard(@RequestParam("id") Long id) {
        hockeyScoreBoardRepository.delete(hockeyScoreBoardRepository.findById(id).get());
        return "redirect:/hockey/boards";
    }

    @PostMapping("/plus")
//    @ResponseBody
    public String plus(@RequestParam("action") String param, @RequestParam("id") Long id) {
        switch (param) {
            case "minutesPlus" : ++minutes;
                break;
            case "minutesMinus" : --minutes;
                break;
            case "secondsPlus" : ++seconds;
                break;
            case "secondsMinus" : --seconds;
                break;
            case "homeScorePlus" : ++homeScore;
                break;
            case "homeScoreMinus" : --homeScore;
                break;
            case "periodPlus" : ++period;
                break;
            case "periodMinus" : --period;
                break;
            case "awayScorePlus" : ++awayScore;
                break;
            case "awayScoreMinus" : --awayScore;
                break;
        }
        return "redirect:/hockey/board/" + id;
    }

//    @GetMapping("/seconds")
//    @ResponseBody
//    public int seconds() {
//        return seconds;
//    }
//
//    @GetMapping("/minutes")
//    @ResponseBody
//    public int minutes() {
//        return minutes;
//    }

    @GetMapping("/time")
    @ResponseBody
    public int[] time() {
        return new int[]{homeScore, minutes, seconds, awayScore};
    }

    @GetMapping("/broadcast/{id}")
    public String broadcast(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyScoreBoardRepository.findById(id).get());
        model.addAttribute("minutes", minutes);
        model.addAttribute("seconds", seconds);
        model.addAttribute("homeScore", homeScore);
        model.addAttribute("awayScore", awayScore);
        model.addAttribute("period", period);
        return "broadcast";
    }
    String ss = "Start";
    Runnable forwardTask = () -> {
//        while (ss.equals("Stop")) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
        if (ss.equals("Stop")){

            if (minutes < 20 && seconds == 59) {
                minutes++;
                seconds = 0;
            } else if (minutes == 19 && seconds == 59) {
                minutes = 0;
                seconds = 0;
                period++;
                ss = "Start";
            } else seconds++;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    };

//    Runnable countdownTask = () -> {
//        while (ss.equals("Stop")) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//
//                if (hockeyScoreBoard.getMinutes() > 0 && hockeyScoreBoard.getSeconds() == 0) {
//                    hockeyScoreBoard.setMinutes(hockeyScoreBoard.getMinutes() - 1);
//                    hockeyScoreBoard.setSeconds(59);
//                } else if (hockeyScoreBoard.getMinutes() == 0 && hockeyScoreBoard.getSeconds() == 0){
//                    hockeyScoreBoard.setMinutes(times.get(0));
//                    hockeyScoreBoard.setPeriod(hockeyScoreBoard.getPeriod() + 1);
//                    ss = "Start";
//                } else hockeyScoreBoard.setSeconds(hockeyScoreBoard.getSeconds() - 1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    };

ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    boolean f = true;

    @PostMapping("/start")
    @ResponseBody
    public void start() {
        if (ss.equals("Start")) {
            ss = "Stop";
//            if (countdown) executorService.execute(countdownTask);
            if (f){
                f = false;
                if (countdown) scheduledExecutorService.scheduleAtFixedRate(forwardTask,1,1, TimeUnit.SECONDS);
                    //            else executorService.execute(forwardTask);
                else scheduledExecutorService.scheduleAtFixedRate(forwardTask,1,1, TimeUnit.SECONDS);
            }
        } else ss = "Start";
    }
}
