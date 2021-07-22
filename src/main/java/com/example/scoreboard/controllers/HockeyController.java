package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.service.HockeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;


@Controller
@RequiredArgsConstructor
@RequestMapping("/hockey")
public class HockeyController {
    private final HockeyService hockeyService;

    @GetMapping("/main")
    public String main() {
        return "index";
    }

    @GetMapping("/boards")
    public String showBoards(Model model) {
        model.addAttribute("boards", hockeyService.findAll());
        return "my_boards";
    }

    @GetMapping("/create")
    public String createBoard(Model model) {
        model.addAttribute("board", new HockeyScoreBoard());
        return "board_form";
    }

    @GetMapping("/board/{id}")
    public String openBoard(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyService.findById(id));
        model.addAttribute("ttt", hockeyService.getCurrentTime());
        model.addAttribute("isCountdownModeSelected", hockeyService.isCountdownModeSelected());
        model.addAttribute("startStop", hockeyService.getStartStop());
        return "admin_board";
    }

    @GetMapping("/edit/{id}")
    public String editBoard(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyService.findById(id));
        return "board_form";
    }

    @PostMapping("/save")
    public String saveOrUpdateBoard(@ModelAttribute HockeyScoreBoard board) {
        hockeyService.save(board);
        return "redirect:/hockey/boards";
    }

    @PostMapping("/delete")
    public String deleteBoard(@RequestParam("id") Long id) {
        hockeyService.deleteById(id);
        return "redirect:/hockey/boards";
    }

    @PostMapping("/plus")
//    @ResponseBody
    public String plus(@RequestParam("action") String param, @RequestParam("id") Long id) {
        hockeyService.plusOrMinusOne(param, id);
        return "redirect:/hockey/board/" + id;
    }


//    @PostMapping("/pluss")
//    @ResponseBody
//    public int pluss() {
//        return i.incrementAndGet();
//    }


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
    public Integer[] time() {
        Integer[] arr = new Integer[]{hockeyService.getHockeyScoreBoardRepository().findById(2L).get().getHomeScore(),
                hockeyService.getCurrentTime(),
                hockeyService.getHockeyScoreBoardRepository().findById(2L).get().getAwayScore(),
                hockeyService.getHockeyScoreBoardRepository().findById(2L).get().getPeriod()};
        return arr;
    }

    @GetMapping("/broadcast/{id}")
    public String broadcast(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyService.findById(id));
        return "broadcast";
    }


    @PostMapping("/start")
    @Transactional
    @ResponseBody
    public String start() {
        if (hockeyService.getStartStop().equals("Start")) {
            hockeyService.setStartStop("Stop");

            if (hockeyService.isFirstStart()) {
                hockeyService.setFirstStart(false);
                hockeyService.getScheduledExecutorService().scheduleAtFixedRate(hockeyService.isCountdownModeSelected() ?
                        () -> {
                            if (hockeyService.getStartStop().equals("Stop")) {
                                if (hockeyService.getCurrentTime() == 0) {
                                    hockeyService.setCurrentTime(hockeyService.getMaxTime());
                                    hockeyService.setStartStop("Start");
                                } else hockeyService.setCurrentTime(hockeyService.getCurrentTime() - 1);
                            }
                        } :
                        () -> {
                            if (hockeyService.getStartStop().equals("Stop")) {
                                if (hockeyService.getCurrentTime() == hockeyService.getMaxTime()) {
                                    hockeyService.setCurrentTime(0);
                                    hockeyService.setStartStop("Start");
                                } else hockeyService.setCurrentTime(hockeyService.getCurrentTime() + 1);
                            }
                        },1,1, TimeUnit.SECONDS);
            }
        } else {
            hockeyService.setStartStop("Start");
            hockeyService.findById(2L).setCurrentTime(hockeyService.getCurrentTime());
        }
        return hockeyService.getStartStop();
    }
}
