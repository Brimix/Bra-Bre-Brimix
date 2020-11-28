package BrimixGaming.BraBreBrimix.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class Game {
    //~ Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date created;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;
    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Score> scores;

    //~ Constructor
    public Game(){
        this.created = new Date();
        this.gamePlayers = new LinkedHashSet<>();
        this.scores = new LinkedHashSet<>();
    }

    public long getId() { return id; }
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }
    public Set<GamePlayer> getGamePlayers() { return gamePlayers; }
    public void setGamePlayers(Set<GamePlayer> gamePlayers) { this.gamePlayers = gamePlayers; }
    public Set<Score> getScores() { return scores; }
    public void setScores(Set<Score> scores) { this.scores = scores; }

    //~ Methods
    public void addGamePlayer(GamePlayer gp){
        gamePlayers.add(gp);
        gp.setGame(this);
    }
    public void addScore(Score score){
        scores.add(score);
        score.setGame(this);
    }
}
