package BrimixGaming.BraBreBrimix.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class Player {
    //~ Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date created;
    private String username;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> scores;

    //~ Constructors
    public Player(){
        this.created = new Date();
        this.gamePlayers = new LinkedHashSet<>();
    }
    public Player(String username) {
        this();
        this.username = username;
    }

    public long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Set<GamePlayer> getGamePlayers() { return gamePlayers; }
    public void setGamePlayers(Set<GamePlayer> gamePlayers) { this.gamePlayers = gamePlayers; }
    public Set<Score> getScores() { return scores; }
    public void setScores(Set<Score> scores) { this.scores = scores; }

    //~ Methods
    public void addGamePlayer(GamePlayer gp){
        gamePlayers.add(gp);
        gp.setPlayer(this);
    }
    public void addScore(Score score){
        scores.add(score);
        score.setPlayer(this);
    }
}
