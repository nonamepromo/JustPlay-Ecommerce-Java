package it.univaq.disim.mwt.justplay.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Utente {

	private Long id;

	@Size(max = 200)
	private String nome;

	@Size(max = 200)
	private String cognome;

	private String username;

	private String password;
	
}
