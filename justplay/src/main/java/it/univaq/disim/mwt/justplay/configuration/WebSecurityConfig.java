package it.univaq.disim.mwt.justplay.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.headers().disable().csrf().disable().formLogin().loginPage("/common/login")
				.loginProcessingUrl("/login").failureUrl("/common/login?error=invalidlogin")
				.defaultSuccessUrl("/videogiochi/list?size=6&page=1", false).and().logout()
				.logoutSuccessUrl("/videogiochi/list?size=6&page=1").and().exceptionHandling()
				.accessDeniedPage("/common/accessdenied").and().authorizeRequests()
				// Specificare le url che sono soggette ad autenticazione ed autorizzazione
				.antMatchers("/", "/static/**", "/favicon.ico").permitAll().antMatchers("/common/profilo")
				.authenticated().antMatchers("/videogiochi/addGameToSellinglistProva").authenticated()
				.antMatchers("/videogiochi/createConversazione").authenticated()
				.antMatchers("/videogiochi/gameFromPlayedlist").authenticated()
				.antMatchers("/videogiochi/gameFromWishlist").authenticated()
				.antMatchers("/videogiochi/gameLiked").authenticated()
				.antMatchers("/videogiochi/addGameToLikedlist").authenticated()
				.antMatchers("/videogiochi/removeGameFromSellinglist").authenticated()
				.antMatchers("/videogiochi/addCommento").authenticated()
				.antMatchers("/common/profilo/removeGameFromWishlist").authenticated()
				.antMatchers("/common/profilo/removeGameFromPlayedlist").authenticated()
				.antMatchers("/common/profilo/removeGameFromSellinglist").authenticated()
				.antMatchers("/common/conversations-list").authenticated();
	}
}
