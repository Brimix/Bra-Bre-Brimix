package BrimixGaming.BraBreBrimix;

import BrimixGaming.BraBreBrimix.Model.Game;
import BrimixGaming.BraBreBrimix.Model.GamePlayer;
import BrimixGaming.BraBreBrimix.Model.Player;
import BrimixGaming.BraBreBrimix.Model.Score;
import BrimixGaming.BraBreBrimix.Repository.GamePlayerRepository;
import BrimixGaming.BraBreBrimix.Repository.GameRepository;
import BrimixGaming.BraBreBrimix.Repository.PlayerRepository;
import BrimixGaming.BraBreBrimix.Repository.ScoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class BraBreBrimixApplication {
	public static void main(String[] args) {
		SpringApplication.run(BraBreBrimixApplication.class, args);
		System.out.println("Tic-Tac-Toe server is up!\n");
	}
	@Bean
	public CommandLineRunner initData(
			PlayerRepository p_rep,
			GameRepository g_rep,
			GamePlayerRepository gp_rep,
			ScoreRepository s_rep
	){
		return (args) -> {
			 Player P1 = new Player("April");
			 Player P2 = new Player("Brian");
			 Player P3 = new Player("Carol");
			 Game G1 = new Game();
			 Game G2 = new Game();
			 Game G3 = new Game();
			 GamePlayer GP1 = new GamePlayer(P1, G1, 'X');
			 GamePlayer GP2 = new GamePlayer(P2, G1, 'O');
			 GamePlayer GP3 = new GamePlayer(P2, G2, 'B');
			 GamePlayer GP4 = new GamePlayer(P3, G2, 'C');
			 GamePlayer GP5 = new GamePlayer(P3, G3, '6');
			 GamePlayer GP6 = new GamePlayer(P1, G3, '9');

			 Score S1 = GP1.getScore('W');
			 Score S2 = GP2.getScore('L');
			 Score S3 = GP3.getScore('N');
			 Score S4 = GP4.getScore('N');
			 Score S5 = GP5.getScore('D');
			 Score S6 = GP6.getScore('D');

			 p_rep.saveAll(List.of(P1, P2, P3));
			 g_rep.saveAll(List.of(G1, G2, G3));
			 gp_rep.saveAll(List.of(GP1, GP2, GP3, GP4, GP5, GP6));
			 s_rep.saveAll(Arrays.asList(S1, S2, S3, S4, S5, S6).stream()
					 		.filter(s -> s != null)
					 		.collect(toList()));
		};
	}
}
