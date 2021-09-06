package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.service.HockeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hockey")
public class HockeyController {
    private final HockeyService hockeyService;

    @Autowired
    public HockeyController(HockeyService hockeyService) {
        this.hockeyService = hockeyService;
    }

//    @Lookup
//    public HockeyService getHockeyService() {
//        return null;
//    }

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
    public String createBoard() {
        return "redirect:/hockey/board/" + hockeyService.save(new HockeyScoreBoard()).getId();
    }

    @GetMapping("/board/{id}")
    public String openBoard(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyService.findById(id));
        return "admin_board";
    }

    @GetMapping("/broadcast/{id}")
    public String broadcast(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyService.findById(id));
        return "broadcast";
    }

    @PostMapping("/delete")
    public String deleteBoard(@RequestParam("id") Long id) {
        hockeyService.deleteById(id);
        return "redirect:/hockey/boards";
    }

    @PostMapping("/save")
    public String saveOrUpdateBoard(@ModelAttribute HockeyScoreBoard board) {
        hockeyService.save(board);
        return "redirect:/hockey/boards";
    }

    @PostMapping("/plus_minus")
    @Transactional
    @ResponseBody
    public String plusOrMinusOne(@RequestParam("action") String param, @RequestParam("id") Long id) {
        return hockeyService.plusOrMinusOne(param, id);
    }

    @GetMapping("/get_board")
    @ResponseBody
    public HockeyScoreBoard getBoard(@RequestParam("id") Long id) {
        return hockeyService.findById(id);
    }

    @PostMapping("/mode")
    @Transactional
    @ResponseBody
    public void changeMode(@RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        board.setCountdownModeSelected(!board.isCountdownModeSelected());

        if (board.isCountdownModeSelected() && board.getCurrentTime() == 0) {
            board.setCurrentTime(board.getMaxTime());
        } else if (board.isCountdownModeSelected() && board.getCurrentTime() != board.getMaxTime() && board.getCurrentTime() != 0){
            board.setCurrentTime((board.getCurrentTime() - board.getMaxTime()) * -1);
        } else if (!board.isCountdownModeSelected() && board.getCurrentTime() != 0){
            board.setCurrentTime(board.getMaxTime() - board.getCurrentTime());
        }
    }

    @PostMapping("/change_input")
    @Transactional
    @ResponseBody
    public void changeInput(@RequestParam("value") String value, @RequestParam("name") String name, @RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        switch (name) {
            case "homeName":
                board.setHomeName(value);
                break;
            case "awayName":
                board.setAwayName(value);
                break;
            case "maxTime":
                board.setMaxTime(Integer.parseInt(value) * 60);
                break;
        }
        hockeyService.save(board);
    }

    @PostMapping("/reset")
    @Transactional
    @ResponseBody
    public void reset(@RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        board.setHomeName("");
        board.setAwayName("");
        board.setHomeScore(0);
        board.setAwayScore(0);
        board.setPeriod(1);
        board.setCurrentTime(board.isCountdownModeSelected() ? board.getMaxTime() : 0);
    }

    @PostMapping("/swap")
    @Transactional
    @ResponseBody
    public HockeyScoreBoard swap(@RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        String tempName = board.getHomeName();
        board.setHomeName(board.getAwayName());
        board.setAwayName(tempName);
        return board;
    }

    @PostMapping("/start")
    @Transactional
    @ResponseBody
    public String start(@RequestParam("id") Long id) {
        return hockeyService.start(id);
    }
}
