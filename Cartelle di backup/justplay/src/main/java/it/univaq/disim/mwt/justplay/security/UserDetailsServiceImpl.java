package it.univaq.disim.mwt.justplay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UtenteService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utente utente;
		try {
			utente = service.findUtenteByUsername(username);
			if (utente != null) {
				return new UserDetailsImpl(utente);
			} else {
				throw new UsernameNotFoundException("utente non trovato");
			}

		} catch (BusinessException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("utente non trovato");
		}
	}

}