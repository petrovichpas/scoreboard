package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyBoard;
import com.example.scoreboard.entites.User;
import com.example.scoreboard.service.HockeyService;
import com.example.scoreboard.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;

@Controller
@RequestMapping("/hockey")
public class HockeyController {
    private final HockeyService hockeyService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public HockeyController(HockeyService hockeyService, UserServiceImpl userServiceImpl) {
        this.hockeyService = hockeyService;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/boards")
    public String showBoards(Model model, Principal principal) {
//        model.addAttribute("boards", hockeyService.findAll());
        model.addAttribute("boards", userServiceImpl.findByEmail(principal.getName()).getBoardList());
        return "my_boards";
    }

    @GetMapping("/create")
    public String createBoard(Principal principal) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return "redirect:/hockey/board/" + hockeyService.save(new HockeyScoreBoard(userService.findByEmail(auth.getName()))).getId();
        return "redirect:/hockey/board/" + hockeyService.save(new HockeyBoard(userServiceImpl.findByEmail(principal.getName()))).getId();
    }

    @GetMapping("/board/{id}")
    public String openBoard(Model model, @PathVariable Long id, Principal principal, HttpServletResponse response) throws IOException {
        HockeyBoard board = hockeyService.findById(id);
        User user = userServiceImpl.findByEmail(principal.getName());

        if (!user.getBoardList().contains(board)) response.sendError(HttpServletResponse.SC_FORBIDDEN);
        model.addAttribute("board", board);
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
    @Transactional
    public String saveOrUpdateBoard(@ModelAttribute HockeyBoard board) {
        hockeyService.save(board);
        return "redirect:/hockey/boards";
    }

    @PostMapping("/plus_minus")
    @Transactional
    @ResponseBody
    public String plusOrMinusOne(@RequestParam("action") String param, @RequestParam("id") Long id) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return hockeyService.plusOrMinusOne(param, id);
    }

    @PostMapping("/mode")
    @ResponseStatus(value = HttpStatus.OK)
    public void changeMode(@RequestParam("id") Long id) {
        HockeyBoard board = hockeyService.findById(id);
        board.setCountdownModeSelected(!board.isCountdownModeSelected());

        if (board.isCountdownModeSelected() && board.getCurrentTime() == 0) {
            board.setCurrentTime(board.getMaxTime());
        } else if (board.isCountdownModeSelected() && board.getCurrentTime() != board.getMaxTime() && board.getCurrentTime() != 0){
            board.setCurrentTime((board.getCurrentTime() - board.getMaxTime()) * -1);
        } else if (!board.isCountdownModeSelected() && board.getCurrentTime() != 0){
            board.setCurrentTime(board.getMaxTime() - board.getCurrentTime());
        }
        saveOrUpdateBoard(board);
    }

    @GetMapping("/get_board")
    public ResponseEntity getBoard(@RequestParam("id") Long id) {
        HashMap<String, String> map = new HashMap();
        HockeyBoard board = hockeyService.findById(id);

        map.put("homeName", board.getHomeName());
        map.put("awayName", board.getAwayName());
        map.put("homeScore", board.getHomeScore().toString());
        map.put("awayScore", board.getAwayScore().toString());
        map.put("period", board.getPeriod());
        map.put("currentTime", hockeyService.timeToString(board.getCurrentTime()));
        map.put("penaltyTime", hockeyService.timeToString(board.getPenaltyTime()));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/change_input")
    @Transactional
    @ResponseStatus(value = HttpStatus.OK)
    public void changeInput(@RequestParam("value") String value, @RequestParam("name") String name, @RequestParam("id") Long id) {
        HockeyBoard board = hockeyService.findById(id);

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
            case "penaltyTime":
                board.setPenaltyTime(hockeyService.timeToSeconds(value));
                break;
            case "penaltyNumber":
                board.setPenaltyNumber(value);
                break;
//                else {
//                    if (value.length() > 3) board.setPenaltyTime(Integer.parseInt(value.substring(0, 2)) + Integer.parseInt(value.substring(2, 4)) * 60);
//                    if (value.length() == 3) board.setPenaltyTime(Integer.parseInt(value.substring(0, 2)) + Integer.parseInt(value.substring(2, 3)) * 60);
//                    if (value.length() == 2) board.setPenaltyTime(Integer.parseInt(value.substring(0, 2)));
//                    if (value.length() == 1) board.setPenaltyTime(Integer.parseInt(value.substring(0, 1)));
//                }
        }
        saveOrUpdateBoard(board);
    }

    @PostMapping("/reset")
    public String reset(@RequestParam("id") Long id) {
        HockeyBoard board = hockeyService.findById(id);
        board.setHomeName("");
        board.setAwayName("");
        board.setHomeScore(0);
        board.setAwayScore(0);
        board.setPeriod("1");
        board.setCurrentTime(board.isCountdownModeSelected() ? board.getMaxTime() : 0);
        saveOrUpdateBoard(board);
        return "redirect:/hockey/board/" + id;
    }

    @PostMapping("/swap")
    @ResponseBody
    public HockeyBoard swap(@RequestParam("id") Long id) {
        HockeyBoard board = hockeyService.findById(id);
        String tempName = board.getHomeName();
        board.setHomeName(board.getAwayName());
        board.setAwayName(tempName);
        saveOrUpdateBoard(board);
        return board;
    }

    @PostMapping("/start")
    @Transactional
    @ResponseBody
    public String start(@RequestParam("id") Long id) {
        return hockeyService.start(id);
    }
}
