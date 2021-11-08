package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "videogiochi_giocati")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideogiocoGiocato extends AbstractPersistableEntity {

	@ManyToOne
	@JoinColumn(name = "fk_utente")
	Utente utente;

	@ManyToOne
	@JoinColumn(name = "fk_videogioco")
	Videogioco videogioco;
}
