package it.univaq.disim.mwt.justplay.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Messaggio {
    
	private Long id;

	private Long fk_mittente;

	private Long fk_conversazione;

    private String contenuto;

}
