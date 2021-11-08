package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "provreg")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public class Pincodes extends AbstractPersistableEntity{

	private String provincia;
	
	private String regione;

}

