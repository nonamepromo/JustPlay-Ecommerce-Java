package it.univaq.disim.mwt.justplay.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.univaq.disim.mwt.justplay.domain.Admin;
import it.univaq.disim.mwt.justplay.domain.Ruolo;
import it.univaq.disim.mwt.justplay.domain.Utente;

public class UserDetailsImpl implements UserDetails {
	
	private static final String ROLE_PREFIX = "ROLE_";
	private static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority(ROLE_PREFIX + "admin");
	
	private Utente utente;

	public UserDetailsImpl(Utente utente) {
		super();
		this.utente = utente;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		
		for (Ruolo ruolo : utente.getRuoli()) {
			result.add(new SimpleGrantedAuthority(ROLE_PREFIX + ruolo.getNome()));
		}
		if (utente instanceof Admin) {
			result.add(ROLE_ADMIN);
		}
		return result;
	}

	@Override
	public String getPassword() {
		return utente.getPassword();
	}

	@Override
	public String getUsername() {
		return utente.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "UserDetailsImpl [username=" + utente.getUsername() + "]";
	}

	public Utente getUtente() {
		return utente;
	}
	

}
