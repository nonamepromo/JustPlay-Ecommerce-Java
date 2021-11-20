package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "commenti")
//@Document(collection = "commenti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//public class Commento {
public class Commento extends AbstractPersistableEntity {

	@NotNull
	private String contenuto;
	
	@OneToOne
	private Utente utente;
	
	@ManyToOne
	@JoinColumn(name = "videogioco_id")
	private Videogioco videogioco;
	
}
