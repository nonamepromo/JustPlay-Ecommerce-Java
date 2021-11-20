package it.univaq.disim.mwt.justplay.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.sun.istack.NotNull;

import it.univaq.disim.mwt.justplay.presentation.validator.OnCreate;
import it.univaq.disim.mwt.justplay.presentation.validator.OnUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "videogiochi")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public class Videogioco extends AbstractPersistableEntity {

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String titolo;

	@Column(name = "anno_di_uscita")
	private int annoDiUscita;

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String descrizione;

	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String descrizioneEN;

	@NotNull
	private boolean ps4;

	@NotNull
	private boolean xbox;

	@NotNull
	private boolean pc;

	@Column(name = "image_url")
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 2500, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String imageUrl;

	// QUESTO NON è DETTO CHE SERVA PER FORZA, BASTA QUELLO IN UTENTE
	@ManyToMany(mappedBy = "videogiochiGiocati", fetch = FetchType.EAGER)
	@Basic(fetch = FetchType.LAZY)
	private Set<Utente> giocati = new HashSet<>();

	// QUESTO NON è DETTO CHE SERVA PER FORZA, BASTA QUELLO IN UTENTE
	@ManyToMany(mappedBy = "videogiochiDesiderati", fetch = FetchType.EAGER)
	@Basic(fetch = FetchType.LAZY)
	private Set<Utente> desiderati = new HashSet<>();

	// QUESTO NON è DETTO CHE SERVA PER FORZA, BASTA QUELLO IN UTENTE
	@ManyToMany(mappedBy = "videogiochiPiaciuti", fetch = FetchType.EAGER)
	@Basic(fetch = FetchType.LAZY)
	private Set<Utente> piaciuti = new HashSet<>();

	// QUESTO NON è DETTO CHE SERVA PER FORZA, BASTA QUELLO IN UTENTE
	@ManyToMany(mappedBy = "videogiochiNonPiaciuti", fetch = FetchType.EAGER)
	@Basic(fetch = FetchType.LAZY)
	private Set<Utente> nonPiaciuti = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "videogioco")
	private Set<VideogiocoInVendita> videogiochiInVendita;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "videogioco")
	private Set<Amazon> amazon;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "videogioco")
	private Set<GameStop> gamestop;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Videogioco videogioco = (Videogioco) o;
		return Objects.equals(titolo, videogioco.titolo) && Objects.equals(annoDiUscita, videogioco.annoDiUscita)
				&& Objects.equals(descrizione, videogioco.descrizione)
				&& Objects.equals(descrizioneEN, videogioco.descrizioneEN) && Objects.equals(ps4, videogioco.ps4)
				&& Objects.equals(xbox, videogioco.xbox) && Objects.equals(pc, videogioco.pc)
				&& Objects.equals(imageUrl, videogioco.imageUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo, annoDiUscita, descrizione, descrizioneEN, ps4, xbox, pc, imageUrl);
	}

}
