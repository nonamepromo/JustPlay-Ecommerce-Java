package it.univaq.disim.mwt.justplay.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideogiocoDesiderato {

	private Long fkUtente;

	private Long fkVideogioco;
}
