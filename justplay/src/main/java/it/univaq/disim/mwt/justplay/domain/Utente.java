package it.univaq.disim.mwt.justplay.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Utente {

	private Long id;
	private String nome;
	private String cognome;
	private String email;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String username;
	private String password;

	private Set<Ruolo> ruoli = new HashSet<>();
}
