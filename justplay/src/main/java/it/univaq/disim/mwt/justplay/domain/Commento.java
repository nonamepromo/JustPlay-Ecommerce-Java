package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "commenti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Commento {
	
	private String hobbitNebri;
	
	private String di104DiAlessandro;

	@NotNull
	private String contenuto;
	
	@OneToOne
	private Utente utente;
	
	@OneToOne
	private Videogioco videogioco;
	
}
