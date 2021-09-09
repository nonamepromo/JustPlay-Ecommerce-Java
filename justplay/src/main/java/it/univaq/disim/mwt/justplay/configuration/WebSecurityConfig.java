package it.univaq.disim.mwt.justplay.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
				.defaultSuccessUrl("/videogiochi/list?platform=all&index=1", false).and().logout()
				.logoutSuccessUrl("/videogiochi/list?platform=all&index=1").and().exceptionHandling()
				.accessDeniedPage("/common/accessdenied").and().authorizeRequests()
				// Specificare le url che sono soggette ad autenticazione ed autorizzazione
				.antMatchers("/", "/static/**", "/favicon.ico").permitAll().antMatchers("/common/profilo")
				.authenticated().antMatchers("/videogiochi/addGameToSellinglistProva").authenticated();
		// .antMatchers("/areessd/**", "/ssds/**").hasAnyRole("amministratore")
		// .antMatchers("/insegnamenti/**", "/appelli/**").hasAnyRole("docente");
	}

}
