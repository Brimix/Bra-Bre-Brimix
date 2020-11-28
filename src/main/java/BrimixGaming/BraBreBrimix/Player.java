package BrimixGaming.BraBreBrimix;

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

    //~ Methods
    public void addGamePlayer(GamePlayer gp){
        gamePlayers.add(gp);
        gp.setPlayer(this);
    }
}
