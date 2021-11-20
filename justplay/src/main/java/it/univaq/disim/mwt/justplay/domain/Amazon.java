package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "amazon")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Amazon extends AbstractPersistableEntity{

	@ManyToOne
	@JoinColumn(name = "videogioco_id")
	private Videogioco videogioco;
	
	@NotNull(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String piattaforma;
	
	@NotNull(groups = { OnCreate.class, Default.class })
	private String amazonUrl;

	@NotNull(groups = { OnCreate.class, Default.class })
	private double prezzoAmazon;
	
}
