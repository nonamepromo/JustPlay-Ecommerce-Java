package it.univaq.disim.mwt.justplay.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideogiocoGiocato {

	private Long fk_utente;

	private Long fk_videogioco;
	
}
