package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.groups.Default;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.disim.mwt.justplay.presentation.validator.ValidEmail;
import it.univaq.disim.mwt.justplay.presentation.validator.OnCreate;
import it.univaq.disim.mwt.justplay.presentation.validator.OnUpdate;
import it.univaq.disim.mwt.justplay.presentation.validator.UsernameUnique;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "videogiochi")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Videogioco extends AbstractPersistableEntity {

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String titolo;

	@Column(name = "anno_di_uscita")
	private int annoDiUscita;

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String descrizione;

	@Column(name = "ps4_url")
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String ps4Url;

	@Column(name = "xbox_url")
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String xboxUrl;

	@Column(name = "pc_url")
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String pcUrl;

	@Column(name = "image_url")
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String imageUrl;
}
