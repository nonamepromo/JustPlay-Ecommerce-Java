package it.univaq.disim.mwt.justplay.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Conversazione {

	private Long id;

	private Long fk_utente1;

	private Long fk_utente2;

	private String nome_utente1;

	private String nome_utente2;
	
	private Date data;
	
}
