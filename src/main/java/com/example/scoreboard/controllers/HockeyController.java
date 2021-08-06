package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.service.HockeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        HockeyScoreBoard board = new HockeyScoreBoard();
        return "redirect:/hockey/board/" + hockeyService.save(board).getId();
    }

    @GetMapping("/board/{id}")
    public String openBoard(Model model, @PathVariable Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
//        hockeyService.setCurrentTime(board.getCurrentTime());
//        hockeyService.setMaxTime(board.getMaxTime());

        model.addAttribute("board", board);
//        model.addAttribute("currentTime", hockeyService.getCurrentTime());
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

    @PostMapping("/plus-minus")
    @Transactional
    @ResponseBody
    public Integer plusOrMinusOne(@RequestParam("action") String param, @RequestParam("id") Long id) {
        return hockeyService.plusOrMinusOne(param, id);
    }

    @GetMapping("/time")
    @ResponseBody
    public Integer[] time(@RequestParam("id") Long id) {
        Integer[] arr = new Integer[]{
                hockeyService.findById(id).getHomeScore(),
                hockeyService.findById(id).getCurrentTime(),
                hockeyService.findById(id).getAwayScore(),
                hockeyService.findById(id).getPeriod()
        };
        return arr;
    }

    @PostMapping("/mode")
    @Transactional
    @ResponseBody
    public void changeMode(@RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        hockeyService.setCountdownModeSelected(hockeyService.isCountdownModeSelected() ? false : true);

        if (hockeyService.isCountdownModeSelected() && board.getCurrentTime() == 0) {
            board.setCurrentTime(board.getMaxTime());
        } else if (hockeyService.isCountdownModeSelected() && board.getCurrentTime() != board.getMaxTime() && board.getCurrentTime() != 0){
            board.setCurrentTime((board.getCurrentTime() - board.getMaxTime()) * -1);
        } else if (!hockeyService.isCountdownModeSelected() && board.getCurrentTime() != 0){
            board.setCurrentTime(board.getMaxTime() - board.getCurrentTime());
        }
    }

    @PostMapping("/name")
    @Transactional
    @ResponseBody
    public void changeField(@RequestParam("value") String value, @RequestParam("name") String name, @RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        if (name.equals("homeName")) board.setHomeName(value);
        else if(name.equals("awayName")) board.setAwayName(value);
        else if(name.equals("maxTime")) board.setMaxTime(Integer.parseInt(value) * 60);
    }

    @GetMapping("/broadcast/{id}")
    public String broadcast(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyService.findById(id));
        return "broadcast";
    }


    @PostMapping("/start")
    @Transactional
    @ResponseBody
    public String start(@RequestParam("id") Long id) {
        if (hockeyService.getStartStop().equals("Start")) {
            hockeyService.setStartStop("Stop");
            if (hockeyService.isFirstStart()) {
                hockeyService.setFirstStart(false);
               hockeyService.start(id);

//                Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
//                    HockeyScoreBoard board = hockeyService.findById(id);
//
//                            if (hockeyService.getStartStop().equals("Stop")) {
//                                if (hockeyService.isCountdownModeSelected()){
//                                    if (board.getCurrentTime() <= 0) {
//                                        board.setCurrentTime(board.getMaxTime());
//                                        hockeyService.setStartStop("Start");
//                                    } else board.setCurrentTime(board.getCurrentTime() - 1);
//                                } else {
//                                    if (board.getCurrentTime() >= board.getMaxTime()) {
//                                        board.setCurrentTime(0);
//                                        hockeyService.setStartStop("Start");
//                                    } else board.setCurrentTime(board.getCurrentTime() + 1);
//                                }
//                            }
//
//                    hockeyService.save(board);
//                            } ,1,1, TimeUnit.SECONDS);
            }
        } else {
            hockeyService.setStartStop("Start");
        }
        return hockeyService.getStartStop();
    }
}
