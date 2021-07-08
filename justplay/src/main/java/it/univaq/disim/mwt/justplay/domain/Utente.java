package it.univaq.disim.mwt.justplay.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import it.univaq.disim.mwt.justplay.security.PasswordMatches;
import it.univaq.disim.mwt.justplay.security.ValidEmail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class Utente {

	private Long id;
	private String nome;
	private String cognome;

	@ValidEmail
	private String email;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascita;
	private String username;
	private String password;
	private String matchingPassword;
	private Set<Ruolo> ruoli = new HashSet<>();
}
