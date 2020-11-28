package BrimixGaming.BraBreBrimix.DTO;

import BrimixGaming.BraBreBrimix.Model.Game;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class GameDTO {
    public static Map<String, Object> standard(Game game){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreated());
        dto.put("gamePlayers", game.getGamePlayers().stream()
                .map(gp -> gp.getId())
                .collect(toList()));
        dto.put("scores", game.getScores().stream()
                .map(s -> s.getId())
                .collect(toList()));
        return dto;
    }
}
