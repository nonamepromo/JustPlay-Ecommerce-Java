package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import it.univaq.disim.mwt.justplay.presentation.validator.OnCreate;
import it.univaq.disim.mwt.justplay.presentation.validator.OnUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "videogiochi_in_vendita")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideogiocoInVendita extends AbstractPersistableEntity {

	@ManyToOne
	@JoinColumn(name = "utente_id")
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "videogioco_id")
	private Videogioco videogioco;
	
	@NotNull(groups = { OnCreate.class, Default.class })
	private int prezzo;

	@NotNull(groups = { OnCreate.class, Default.class })
	private int prezzoSpedizione;

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	public String provincia;
	
	@NotNull(groups = { OnCreate.class, Default.class })
	@Size(min = 2, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String piattaforma;
}
