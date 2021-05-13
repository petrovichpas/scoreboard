package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.repositories.HockeyScoreBoardRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.timer.Timer;
import java.util.List;


@Controller
@RequestMapping("/hockey")
public class HockeyController {

    private HockeyScoreBoardRepository hockeyScoreBoardRepository;

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

    @Autowired
    public HockeyController(HockeyScoreBoardRepository hockeyScoreBoardRepository) {
        this.hockeyScoreBoardRepository = hockeyScoreBoardRepository;
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
}
