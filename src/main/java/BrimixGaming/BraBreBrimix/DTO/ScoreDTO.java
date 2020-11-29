package BrimixGaming.BraBreBrimix.DTO;

import BrimixGaming.BraBreBrimix.Model.Score;

import java.util.LinkedHashMap;
import java.util.Map;


public class ScoreDTO {
    public static Map<String, Object> standard(Score score){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", score.getId());
        dto.put("ended", score.getEnded());
        dto.put("score", score.getResult());
        dto.put("player", score.getPlayer().getUsername());
        dto.put("game", score.getGame().getId());
        return dto;
    }
}
