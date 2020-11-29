package BrimixGaming.BraBreBrimix.DTO;

import BrimixGaming.BraBreBrimix.Model.Game;
import BrimixGaming.BraBreBrimix.Model.GamePlayer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class GamePlayerDTO {
    public static Map<String, Object> standard(GamePlayer gp){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gp.getId());
        dto.put("joined", gp.getJoined());
        dto.put("suit", gp.getSuit());
        dto.put("player", gp.getPlayer().getUsername());
        dto.put("game", gp.getGame().getId());
        dto.put("locations", gp.getLocations());
        return dto;
    }
    public static Map<String, Object> gameView(GamePlayer gp1){
        Game game = gp1.getGame();
        GamePlayer gp2 = game.getGamePlayers().stream()
                .filter(gp -> gp.getId() != gp1.getId())
                .findAny()
                .get();

        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("created", game.getCreated());
        dto.put("me", playerInfo(gp1));
        dto.put("rival", playerInfo(gp2));
        return dto;
    }
    private static Map<String, Object> playerInfo(GamePlayer gp) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("username", gp.getPlayer().getUsername());
        dto.put("suit", gp.getSuit());
        dto.put("locations", gp.getLocations());
        return dto;
    }
}
