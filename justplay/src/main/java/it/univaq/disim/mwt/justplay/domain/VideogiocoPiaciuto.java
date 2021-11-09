package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "videogiochi_piaciuti")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideogiocoPiaciuto extends AbstractPersistableEntity {

	@ManyToOne
	@JoinColumn(name = "utente_id")
	Utente utente;

	@ManyToOne
	@JoinColumn(name = "videogioco_id")
	Videogioco videogioco;

	@Column(name = "piaciuto")
	private boolean piaciuto;
	
}
