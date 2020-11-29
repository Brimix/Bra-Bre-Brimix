package BrimixGaming.BraBreBrimix.DTO;

import BrimixGaming.BraBreBrimix.Model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PlayerDTO {
    public static Map<String, Object> standard(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("created", player.getCreated());
        dto.put("username", player.getUsername());
        dto.put("gamePlayers", player.getGamePlayers().stream()
                            .map(gp -> gp.getId())
                            .collect(toList()));
        dto.put("scores", player.getScores().stream()
                            .map(s -> s.getId())
                            .collect(toList()));
        return dto;
    }

    public static Map<String, Object> scoreData(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
//        dto.put("created", player.getCreated());
        dto.put("username", player.getUsername());
//        dto.put("score", player.getScores().stream()
//                .mapToDouble(s -> s.getScore())
//                .sum()
//        );
        dto.put("wins", player.getScores().stream()
                .map(s -> s.getResult())
                .filter(s -> s == 'W')
                .count()
        );
        dto.put("draws", player.getScores().stream()
                .map(s -> s.getResult())
                .filter(s -> s == 'D')
                .count()
        );
        dto.put("loses", player.getScores().stream()
                .map(s -> s.getResult())
                .filter(s -> s == 'L')
                .count()
        );
        return dto;
    }
}
