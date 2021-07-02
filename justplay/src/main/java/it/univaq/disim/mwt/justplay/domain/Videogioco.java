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

	@Size(max = 256)
	private String ps4Url;	

	@Size(max = 256)
	private String xboxUrl;

	@NotBlank
	@Size(max = 256)
	private String pcUrl;

	@NotBlank
	@Size(max = 256)
	private String imageUrl;
}
