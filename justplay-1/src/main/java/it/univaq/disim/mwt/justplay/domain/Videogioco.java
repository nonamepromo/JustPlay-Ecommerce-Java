package it.univaq.disim.mwt.justplay.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Videogioco {

	private Long id;

	@NotBlank
	@Size(max = 200)
	private String titolo;

	@NotBlank
	@Size(max = 200)
	private String piattaforma;	

	@NotBlank
	@Size(max = 200)
	private int annoDiUscita;

	@NotBlank
	@Size(max = 200)
	private String descrizione;
}
