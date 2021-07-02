package it.univaq.disim.mwt.justplay.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.univaq.disim.mwt.justplay.domain.Utente;

public class UserDetailsImpl implements UserDetails {
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	private Utente utente;

	public UserDetailsImpl(Utente utente) {
		super();
		this.utente = utente;
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
		return utente.getId().toString();
	}

	public Utente getUtente() {
		return utente;
	}


	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return null;
	}
	

	
}
