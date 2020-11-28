package BrimixGaming.BraBreBrimix;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BraBreBrimixApplication {
	public static void main(String[] args) {
		SpringApplication.run(BraBreBrimixApplication.class, args);
		System.out.println("Tic-Tac-Toe server is up!\n");
	}
	@Bean
	public CommandLineRunner initData(){
		return (args) -> {
			// Player P1 = new Player('Abril', '');
		};
	}
}
