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
    private String password;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> scores;

    //~ Constructors
    public Player(){
        this.created = new Date();
        this.gamePlayers = new LinkedHashSet<>();
        this.scores = new LinkedHashSet<>();
    }
    public Player(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public long getId() { return id; }
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
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
    public long getWins(){
        return getScores().stream().map(s -> s.getResult())
                .filter(s -> s == 'W').count();
    }
    public long getDraws(){
        return getScores().stream().map(s -> s.getResult())
                .filter(s -> s == 'D').count();
    }
    public long getLoses(){
        return getScores().stream().map(s -> s.getResult())
                .filter(s -> s == 'L').count();
    }
}
