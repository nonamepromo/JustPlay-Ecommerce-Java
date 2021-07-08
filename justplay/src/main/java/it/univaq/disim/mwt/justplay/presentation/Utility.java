package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import it.univaq.disim.mwt.justplay.domain.Admin;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.security.UserDetailsImpl;

public class Utility {

	public static Utente getUtente() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
			return ((UserDetailsImpl) usernamePasswordAuthenticationToken.getPrincipal()).getUtente();
		} else {
			throw new RuntimeException();
		}
	}
	
	public static Admin getAdmin()  {
		Utente utente = getUtente();
		if (utente instanceof Admin) {
			return (Admin) utente;
		} else {
			return null;
		}
	}	
}
