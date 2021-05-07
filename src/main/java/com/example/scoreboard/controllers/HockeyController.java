package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.HockeyScoreBoard;
import com.example.scoreboard.repositories.HockeyScoreBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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


//    @PersistenceContext(unitName = "ds")
//    private EntityManager entityManager;
//
//    @Getter
//    @Setter
//    HockeyScoreBoard hockeyScoreBoard;
//
//    @Getter
//    @Setter
//    String ss = "Start";
//
//    @Getter
//    @Setter
//    List<Integer> times = new ArrayList(Arrays.asList(20, 15, 10, 0));
////    List<String> times = new ArrayList(Arrays.asList("20:00", "15:00", "10:00", "00:00"));
//    ExecutorService executorService = Executors.newCachedThreadPool();
//
//    @Getter
//    @Setter
//    boolean countdown = false;
//
//
//    public String createBoard() {
//        hockeyScoreBoard = new HockeyScoreBoard();
//        return "/hockey_form.xhtml?faces-redirect=true";
//    }
//
//    @Transactional
//    public String saveOrUpdateBoard() {
//        if (hockeyScoreBoard.getId() == null) entityManager.persist(hockeyScoreBoard);
//        entityManager.merge(hockeyScoreBoard);
//        return "/my_boards.xhtml?faces-redirect=true";
//    }
//
//    public String openBoard(HockeyScoreBoard hockeyScoreBoard) {
//        this.hockeyScoreBoard = hockeyScoreBoard;
//        return "/admin_board.xhtml?faces-redirect=true";
//    }
//
//    public String broadcast(HockeyScoreBoard hockeyScoreBoard) {
//        this.hockeyScoreBoard = hockeyScoreBoard;
//        return "/broadcast.xhtml?faces-redirect=true";
//    }
//
//    @Transactional
//    public String editBoard(HockeyScoreBoard hockeyScoreBoard) {
//        this.hockeyScoreBoard = hockeyScoreBoard;
//        return "/hockey_form.xhtml?faces-redirect=true";
//    }
//
//    @Transactional
//    public void deleteBoard(Long id) {
//        entityManager.createQuery("DELETE FROM HockeyScoreBoard h WHERE h.id = :id").setParameter("id", id).executeUpdate();
//    }
//
//    public HockeyScoreBoard findById(Long id) {
//        return entityManager.find(HockeyScoreBoard.class, id);
//    }
//
//    public List<HockeyScoreBoard> getAllBoards() {
//        return entityManager.createQuery("SELECT h FROM HockeyScoreBoard h", HockeyScoreBoard.class).getResultList();
//    }
//
//    @Transactional
//    public void plusOne(ActionEvent event) {
//        switch (event.getComponent().getId()){
//            case "homeScore--1": hockeyScoreBoard.setHomeScore(hockeyScoreBoard.getHomeScore() + 1);
//                break;
//            case "awayScore--1": hockeyScoreBoard.setAwayScore(hockeyScoreBoard.getAwayScore() + 1);
//                break;
//            case "minutes--1": hockeyScoreBoard.setMinutes(hockeyScoreBoard.getMinutes() == 20 ? 20 : hockeyScoreBoard.getMinutes() + 1);
//                break;
//            case "seconds--1": hockeyScoreBoard.setSeconds(hockeyScoreBoard.getSeconds() == 59 ? 0 : hockeyScoreBoard.getSeconds() + 1);
//                break;
//            case "period--1": hockeyScoreBoard.setPeriod(hockeyScoreBoard.getPeriod() + 1);
//                break;
//            case "homePenaltyMinutes1--1": hockeyScoreBoard.setHomePenaltyMinutes1(hockeyScoreBoard.getHomePenaltyMinutes1() + 1);
//                break;
//            case "homePenaltyMinutes2--1": hockeyScoreBoard.setHomePenaltyMinutes2(hockeyScoreBoard.getHomePenaltyMinutes2() + 1);
//                break;
//            case "homePenaltyMinutes3--1": hockeyScoreBoard.setHomePenaltyMinutes3(hockeyScoreBoard.getHomePenaltyMinutes3() + 1);
//                break;
//            case "homePenaltySeconds1--1":
//                hockeyScoreBoard.setHomePenaltySeconds1(hockeyScoreBoard.getHomePenaltySeconds1() == 59 ? 0 : hockeyScoreBoard.getHomePenaltySeconds1() + 1);
//                break;
//            case "homePenaltySeconds2--1":
//                hockeyScoreBoard.setHomePenaltySeconds2(hockeyScoreBoard.getHomePenaltySeconds2() == 59 ? 0 : hockeyScoreBoard.getHomePenaltySeconds2() + 1);
//                break;
//            case "homePenaltySeconds3--1":
//                hockeyScoreBoard.setHomePenaltySeconds3(hockeyScoreBoard.getHomePenaltySeconds3() == 59 ? 0 : hockeyScoreBoard.getHomePenaltySeconds3() + 1);
//                break;
//            case "awayPenaltyMinutes1--1": hockeyScoreBoard.setAwayPenaltyMinutes1(hockeyScoreBoard.getAwayPenaltyMinutes1() + 1);
//                break;
//            case "awayPenaltyMinutes2--1": hockeyScoreBoard.setAwayPenaltyMinutes2(hockeyScoreBoard.getAwayPenaltyMinutes2() + 1);
//                break;
//            case "awayPenaltyMinutes3--1": hockeyScoreBoard.setAwayPenaltyMinutes3(hockeyScoreBoard.getAwayPenaltyMinutes3() + 1);
//                break;
//            case "awayPenaltySeconds1--1":
//                hockeyScoreBoard.setAwayPenaltySeconds1(hockeyScoreBoard.getAwayPenaltySeconds1() == 59 ? 0 : hockeyScoreBoard.getAwayPenaltySeconds1() + 1);
//                break;
//            case "awayPenaltySeconds2--1":
//                hockeyScoreBoard.setAwayPenaltySeconds2(hockeyScoreBoard.getAwayPenaltySeconds2() == 59 ? 0 : hockeyScoreBoard.getAwayPenaltySeconds2() + 1);
//                break;
//            case "awayPenaltySeconds3--1":
//                hockeyScoreBoard.setAwayPenaltySeconds3(hockeyScoreBoard.getAwayPenaltySeconds3() == 59 ? 0 : hockeyScoreBoard.getAwayPenaltySeconds3() + 1);
//                break;
//        }
//        entityManager.merge(hockeyScoreBoard);
//    }
//
//    @Transactional
//    public void minusOne(ActionEvent event) {
//        switch (event.getComponent().getId()){
//            case "homeScore-1": hockeyScoreBoard.setHomeScore(hockeyScoreBoard.getHomeScore() == 0 ? 0 : hockeyScoreBoard.getHomeScore() - 1);
//                break;
//            case "awayScore-1": hockeyScoreBoard.setAwayScore(hockeyScoreBoard.getAwayScore() == 0 ? 0 : hockeyScoreBoard.getAwayScore() - 1);
//                break;
//            case "minutes-1": hockeyScoreBoard.setMinutes(hockeyScoreBoard.getMinutes() == 0 ? 0 : hockeyScoreBoard.getMinutes() - 1);
//                break;
//            case "seconds-1": hockeyScoreBoard.setSeconds(hockeyScoreBoard.getSeconds() == 0 ? 0 : hockeyScoreBoard.getSeconds() - 1);
//                break;
//            case "period-1": hockeyScoreBoard.setPeriod(hockeyScoreBoard.getPeriod() == 0 ? 0 : hockeyScoreBoard.getPeriod() - 1);
//                break;
//            case "homePenaltyMinutes1-1":
//                hockeyScoreBoard.setHomePenaltyMinutes1(hockeyScoreBoard.getHomePenaltyMinutes1() == 0 ? 0 : hockeyScoreBoard.getHomePenaltyMinutes1() - 1);
//                break;
//            case "homePenaltyMinutes2-1":
//                hockeyScoreBoard.setHomePenaltyMinutes2(hockeyScoreBoard.getHomePenaltyMinutes2() == 0 ? 0 : hockeyScoreBoard.getHomePenaltyMinutes2() - 1);
//                break;
//            case "homePenaltyMinutes3-1":
//                hockeyScoreBoard.setHomePenaltyMinutes3(hockeyScoreBoard.getHomePenaltyMinutes3() == 0 ? 0 : hockeyScoreBoard.getHomePenaltyMinutes3() - 1);
//                break;
//            case"homePenaltySeconds1-1":
//                hockeyScoreBoard.setHomePenaltySeconds1(hockeyScoreBoard.getHomePenaltySeconds1() == 0 ? 0 : hockeyScoreBoard.getHomePenaltySeconds1() - 1);
//                break;
//            case "homePenaltySeconds2-1":
//                hockeyScoreBoard.setHomePenaltySeconds2(hockeyScoreBoard.getHomePenaltySeconds2() == 0 ? 0 : hockeyScoreBoard.getHomePenaltySeconds2() - 1);
//                break;
//            case "homePenaltySeconds3-1":
//                hockeyScoreBoard.setHomePenaltySeconds3(hockeyScoreBoard.getHomePenaltySeconds3() == 0 ? 0 : hockeyScoreBoard.getHomePenaltySeconds3() - 1);
//                break;
//            case "awayPenaltyMinutes1-1":
//                hockeyScoreBoard.setAwayPenaltyMinutes1(hockeyScoreBoard.getAwayPenaltyMinutes1() == 0 ? 0 : hockeyScoreBoard.getAwayPenaltyMinutes1() - 1);
//                break;
//            case "awayPenaltyMinutes2-1":
//                hockeyScoreBoard.setAwayPenaltyMinutes2(hockeyScoreBoard.getAwayPenaltyMinutes2() == 0 ? 0 : hockeyScoreBoard.getAwayPenaltyMinutes2() - 1);
//                break;
//            case "awayPenaltyMinutes3-1":
//                hockeyScoreBoard.setAwayPenaltyMinutes3(hockeyScoreBoard.getAwayPenaltyMinutes3() == 0 ? 0 : hockeyScoreBoard.getAwayPenaltyMinutes3() - 1);
//                break;
//            case "awayPenaltySeconds1-1":
//                hockeyScoreBoard.setAwayPenaltySeconds1(hockeyScoreBoard.getAwayPenaltySeconds1() == 0 ? 0 : hockeyScoreBoard.getAwayPenaltySeconds1() - 1);
//                break;
//            case "awayPenaltySeconds2-1":
//                hockeyScoreBoard.setAwayPenaltySeconds2(hockeyScoreBoard.getAwayPenaltySeconds2() == 0 ? 0 : hockeyScoreBoard.getAwayPenaltySeconds2() - 1);
//                break;
//            case "awayPenaltySeconds3-1":
//                hockeyScoreBoard.setAwayPenaltySeconds3(hockeyScoreBoard.getAwayPenaltySeconds3() == 0 ? 0 : hockeyScoreBoard.getAwayPenaltySeconds3() - 1);
//                break;
//        }
//        entityManager.merge(hockeyScoreBoard);
//    }
//
//    Runnable forwardTask = () -> {
//        while (ss.equals("Stop")) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                checkPenaltyTime();
//
//                if (hockeyScoreBoard.getMinutes() < 20 && hockeyScoreBoard.getSeconds() == 59) {
//                    hockeyScoreBoard.setMinutes(hockeyScoreBoard.getMinutes() + 1);
//                    hockeyScoreBoard.setSeconds(0);
//                } else if (hockeyScoreBoard.getMinutes() == 19 && hockeyScoreBoard.getSeconds() == 59) {
//                    hockeyScoreBoard.setMinutes(0);
//                    hockeyScoreBoard.setSeconds(0);
//                    hockeyScoreBoard.setPeriod(hockeyScoreBoard.getPeriod() + 1);
//                    ss = "Start";
//                } else hockeyScoreBoard.setSeconds(hockeyScoreBoard.getSeconds() + 1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    Runnable countdownTask = () -> {
//        while (ss.equals("Stop")) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                checkPenaltyTime();
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
//
//
//    public void checkPenaltyTime(){
//        if (hockeyScoreBoard.getHomePenaltyNumber1() != null) {
//            if (hockeyScoreBoard.getHomePenaltyMinutes1() > 0 && hockeyScoreBoard.getHomePenaltySeconds1() == 0) {
//                hockeyScoreBoard.setHomePenaltyMinutes1(hockeyScoreBoard.getHomePenaltyMinutes1() - 1);
//                hockeyScoreBoard.setHomePenaltySeconds1(59);
//            } else if (hockeyScoreBoard.getHomePenaltyMinutes1() == 0 && hockeyScoreBoard.getHomePenaltySeconds1() == 0){
//                hockeyScoreBoard.setHomePenaltyNumber1(null);
//            } else hockeyScoreBoard.setHomePenaltySeconds1(hockeyScoreBoard.getHomePenaltySeconds1() - 1);
//        }
//    }

//    @Transactional
//    public void start() {
//        entityManager.merge(hockeyScoreBoard);
//        if (ss.equals("Start")) {
//            ss = "Stop";
//            if (countdown){
//                if (!countdownThread.isAlive()){
//                    countdownThread.setDaemon(true);
//                    countdownThread.start();
//                } else countdownThread.resume();
//            } else {
//                if (!forwardThread.isAlive()){
//                    forwardThread.setDaemon(true);
//                    forwardThread.start();
//                } else forwardThread.resume();
//            }
//        } else if (ss.equals("Stop")){
//            ss = "Start";
//
//            if (countdown) countdownThread.suspend();
//            else forwardThread.suspend();
//        }
//    }

//    @Transactional
//    public void start() {
//        entityManager.merge(hockeyScoreBoard);
//        if (ss.equals("Start")) {
//            ss = "Stop";
//            if (countdown) executorService.execute(countdownTask);
//            else executorService.execute(forwardTask);
//        } else ss = "Start";
//    }
}
