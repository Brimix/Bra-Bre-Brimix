package BrimixGaming.BraBreBrimix;

import BrimixGaming.BraBreBrimix.DTO.*;
import BrimixGaming.BraBreBrimix.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    PlayerRepository p_rep;
    @Autowired
    GameRepository g_rep;
    @Autowired
    GamePlayerRepository gp_rep;
    @Autowired
    ScoreRepository s_rep;

    @RequestMapping("/players")
    public List<Map<String, Object>> getAllPlayers(){
        return p_rep.findAll().stream()
            .map(p -> PlayerDTO.standard(p))
            .collect(toList());
    }
    @RequestMapping("/games")
    public List<Map<String, Object>> getAllGames(){
        return g_rep.findAll().stream()
                .map(g -> GameDTO.standard(g))
                .collect(toList());
    }
    @RequestMapping("/gamePlayers")
    public List<Map<String, Object>> getAllGamePlayers(){
        return gp_rep.findAll().stream()
                .map(gp -> GamePlayerDTO.standard(gp))
                .collect(toList());
    }
    @RequestMapping("/scores")
    public List<Map<String, Object>> getAllScores(){
        return s_rep.findAll().stream()
                .map(s -> ScoreDTO.standard(s))
                .collect(toList());
    }
}
