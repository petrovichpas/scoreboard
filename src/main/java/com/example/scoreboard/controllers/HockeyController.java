package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.repositories.HockeyScoreBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


@Controller
public class HockeyController {
    private HockeyScoreBoardRepository hockeyScoreBoardRepository;

    @Autowired
    public HockeyController(HockeyScoreBoardRepository hockeyScoreBoardRepository) {
        this.hockeyScoreBoardRepository = hockeyScoreBoardRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<HockeyScoreBoard> boards = hockeyScoreBoardRepository.findAll();
        model.addAttribute("boards", boards);
        return "index";
    }

    @GetMapping("/broadcast/{id}")
    public String broadcast(@PathVariable Long id, Model model) {
        model.addAttribute("board", hockeyScoreBoardRepository.findById(id));
        return "broadcast";
    }

    @GetMapping("/board/{id}")
    public String board(@PathVariable Long id, Model model) {
        model.addAttribute("board", hockeyScoreBoardRepository.findById(id));
        return "admin_board";
    }
}
