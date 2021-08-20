package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.service.HockeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hockey")
public class HockeyController {
    private HockeyService hockeyService;

    @Autowired
    public HockeyController(HockeyService hockeyService) {
        this.hockeyService = hockeyService;
    }

    @Lookup
    public HockeyService getHockeyService() {
        return null;
    }

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

    @GetMapping("/edit/{id}")
    public String editBoard(Model model, @PathVariable Long id) {
        model.addAttribute("board", hockeyService.findById(id));
        return "board_form";
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

    @PostMapping("/plus-minus")
    @Transactional
    @ResponseBody
    public Integer plusOrMinusOne(@RequestParam("action") String param, @RequestParam("id") Long id) {
        return hockeyService.plusOrMinusOne(param, id);
    }

    @GetMapping("/time")
    @ResponseBody
    public Integer[] time(@RequestParam("id") Long id) {
        return new Integer[]{
                hockeyService.findById(id).getHomeScore(),
                hockeyService.findById(id).getCurrentTime(),
                hockeyService.findById(id).getAwayScore(),
                hockeyService.findById(id).getPeriod()
        };
    }

    @PostMapping("/mode")
    @Transactional
    @ResponseBody
    public void changeMode(@RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        board.setCountdownModeSelected(board.isCountdownModeSelected() ? false : true);

        if (board.isCountdownModeSelected() && board.getCurrentTime() == 0) {
            board.setCurrentTime(board.getMaxTime());
        } else if (board.isCountdownModeSelected() && board.getCurrentTime() != board.getMaxTime() && board.getCurrentTime() != 0){
            board.setCurrentTime((board.getCurrentTime() - board.getMaxTime()) * -1);
        } else if (!board.isCountdownModeSelected() && board.getCurrentTime() != 0){
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

//    @PostMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public String swap(@RequestParam("id") Long id) {
        HockeyScoreBoard board = hockeyService.findById(id);
        String tempName = board.getHomeName();
        board.setHomeName(board.getAwayName());
        board.setAwayName(tempName);
        return "redirect:/hockey/board/" + id;
    }

    @PostMapping("/start")
    @Transactional
    @ResponseBody
    public String start(@RequestParam("id") Long id) {
        return getHockeyService().start(id);
    }
}
