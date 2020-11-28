package BrimixGaming.BraBreBrimix.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class Score {
    //~ Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date ended;
    private char status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PlayerID")
    private Player player;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="GameID")
    private Game game;

    //~ Constructors
    public Score() {
        this.ended = new Date();
    }
    public Score(char status, Player player, Game game) {
        this.status = status;
        this.player = player;
        this.game = game;
    }

    public long getId() { return id; }
    public Date getEnded() { return ended; }
    public void setEnded(Date ended) { this.ended = ended; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
}
