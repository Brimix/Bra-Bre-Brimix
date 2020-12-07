package BrimixGaming.BraBreBrimix;

import BrimixGaming.BraBreBrimix.DTO.*;
import BrimixGaming.BraBreBrimix.Model.Game;
import BrimixGaming.BraBreBrimix.Model.GamePlayer;
import BrimixGaming.BraBreBrimix.Model.Player;
import BrimixGaming.BraBreBrimix.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.LinkedHashMap;
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

    // Declaration of encoder
    @Autowired
    PasswordEncoder passwordEncoder;

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

    @RequestMapping("/game_view/{id}")
    public Map<String, Object> getGameView(@PathVariable Long id){
        return GamePlayerDTO.gameView(gp_rep.findById(id).get());
    }

    @RequestMapping("/leaderboard")
    public List<Map<String, Object>> getLeaderboard(){
        return p_rep.findAll().stream().sorted(Comparator
                .comparingLong(Player::getWins)
                .thenComparingLong(Player::getDraws)
                .thenComparingLong(Player::getLoses)
                .reversed()
            )
            .map(p -> PlayerDTO.scoreData(p))
            .collect(toList());
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> registerPlayer(
            @RequestParam String username,
            @RequestParam String password
        ){
        if (username.isEmpty() || password.isEmpty())
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        p_rep.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> create(Authentication authentication, char suit){
        if(isGuest(authentication))
            return new ResponseEntity<>(makeMap("error", "You are not logged in."), HttpStatus.UNAUTHORIZED);

        Player player = p_rep.findByUsername(authentication.getName()).orElse(null);
        if(player == null)
            return new ResponseEntity<>(makeMap("error", "Database error. Player not found."), HttpStatus.INTERNAL_SERVER_ERROR);

        Game game = new Game();
        GamePlayer gamePlayer = new GamePlayer(player, game, suit);
        g_rep.save(game);
        gp_rep.save(gamePlayer);
        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }

    // Auxiliary methods
    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }
}
