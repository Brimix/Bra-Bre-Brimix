package BrimixGaming.BraBreBrimix.DTO;

import BrimixGaming.BraBreBrimix.Model.GamePlayer;

import java.util.LinkedHashMap;
import java.util.Map;

public class GamePlayerDTO {
    public static Map<String, Object> standard(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("joined", gamePlayer.getJoined());
        dto.put("suit", gamePlayer.getSuit());
        dto.put("player", gamePlayer.getPlayer().getUsername());
        dto.put("game", gamePlayer.getGame().getId());
        dto.put("locations", gamePlayer.getLocations());
        return dto;
    }
}
