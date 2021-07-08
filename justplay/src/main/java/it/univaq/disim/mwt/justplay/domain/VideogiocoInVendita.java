package it.univaq.disim.mwt.justplay.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideogiocoInVendita {

	private Long fk_utente;

	private Long fk_videogioco;
	
	public int prezzo;
	
	public int prezzo_spedizione;

	public String provincia;
	
	public String piattaforma;
}
