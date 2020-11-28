package BrimixGaming.BraBreBrimix.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {
    //~ Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date joined;
    private char suit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PlayerID")
    private Player player;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="GameID")
    private Game game;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations;

    //~ Constructors
    public GamePlayer(){
        this.joined = new Date();
    }
    public GamePlayer(Player player, Game game, char suit) { // Add random pick for suit later
        this();
        player.addGamePlayer(this);
        game.addGamePlayer(this);
        this.suit = suit;
    }

    public long getId() { return id; }
    public Date getJoined() { return joined; }
    public void setJoined(Date joined) { this.joined = joined; }
    public char getSuit() { return suit; }
    public void setSuit(char suit) { this.suit = suit; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
    public List<String> getLocations() { return locations; }
    public void setLocations(List<String> locations) { this.locations = locations; }

    //~ Methods
}
