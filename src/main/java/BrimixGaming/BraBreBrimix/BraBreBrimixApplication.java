package BrimixGaming.BraBreBrimix;

import BrimixGaming.BraBreBrimix.Model.Game;
import BrimixGaming.BraBreBrimix.Model.GamePlayer;
import BrimixGaming.BraBreBrimix.Model.Player;
import BrimixGaming.BraBreBrimix.Model.Score;
import BrimixGaming.BraBreBrimix.Repository.GamePlayerRepository;
import BrimixGaming.BraBreBrimix.Repository.GameRepository;
import BrimixGaming.BraBreBrimix.Repository.PlayerRepository;
import BrimixGaming.BraBreBrimix.Repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

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
			 Player P1 = new Player("April", passwordEncoder().encode("april"));
			 Player P2 = new Player("Brian", passwordEncoder().encode("brian"));
			 Player P3 = new Player("Carol", passwordEncoder().encode("carol"));
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
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUsername(inputName).orElse(null);
			if (player != null) {
				System.out.println("Player found!\n");
				return new User(player.getUsername(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				System.out.println("Player not found!\n");
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/web/login.html").permitAll()
			.antMatchers("/web/js/login.js").permitAll()
			.antMatchers("/web/js/jquery.js").permitAll()
			.antMatchers("/web/**").hasAuthority("USER")
			.antMatchers("/api/**").permitAll()
			.antMatchers("/**").permitAll();
		http.formLogin()
			.usernameParameter("name")
			.passwordParameter("pwd")
			.loginPage("/api/login");
		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}