package it.univaq.disim.mwt.justplay.domain;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.groups.Default;

import it.univaq.disim.mwt.justplay.presentation.validator.OnCreate;
import it.univaq.disim.mwt.justplay.presentation.validator.OnUpdate;
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
	
    @OneToMany(mappedBy = "videogioco")
    Set<VideogiocoPiaciuto> videogiochiPiaciuti;

	@OneToMany(mappedBy = "videogioco")
    Set<VideogiocoGiocato> videogiochiGiocati;

	@OneToMany(mappedBy = "videogioco")
    Set<VideogiocoGiocato> videogiochiDesiderati;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Videogioco videogioco = (Videogioco) o;
		return Objects.equals(titolo, videogioco.titolo) && Objects.equals(annoDiUscita, videogioco.annoDiUscita)
				&& Objects.equals(descrizione, videogioco.descrizione) && Objects.equals(ps4Url, videogioco.ps4Url)
				&& Objects.equals(xboxUrl, videogioco.xboxUrl) && Objects.equals(pcUrl, videogioco.pcUrl)
				&& Objects.equals(imageUrl, videogioco.imageUrl);
	}

	@Override
	public String toString() {
		return "Videogioco{" + "titolo='" + titolo + '\'' + ", annoDiUscita='" + annoDiUscita + '\'' + ", descrizione='"
				+ descrizione + '\'' + ", ps4Url='" + ps4Url + '\'' + ", xboxUrl='" + xboxUrl + ", pcUrl='" + pcUrl
				+ ", imageUrl='" + imageUrl + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo, annoDiUscita, descrizione, ps4Url, xboxUrl, pcUrl, imageUrl);
	}

}
