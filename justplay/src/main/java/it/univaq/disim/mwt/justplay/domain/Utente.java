package it.univaq.disim.mwt.justplay.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.univaq.disim.mwt.justplay.presentation.validator.OnCreate;
import it.univaq.disim.mwt.justplay.presentation.validator.OnUpdate;
import it.univaq.disim.mwt.justplay.presentation.validator.UsernameUnique;
import it.univaq.disim.mwt.justplay.presentation.validator.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "utenti")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public class Utente extends AbstractPersistableEntity {

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String nome;

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String cognome;

	@ValidEmail(groups = { OnCreate.class })
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Email(groups = { OnCreate.class, OnUpdate.class, Default.class })
	@Size(min = 3, max = 45, groups = { OnCreate.class, OnUpdate.class, Default.class })
	@Column(unique = true)
	private String email;

	@UsernameUnique(groups = { OnCreate.class })
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	@Column(unique = true)
	private String username;

	@JsonIgnore
	@NotEmpty(groups = { OnCreate.class, Default.class })
	private String password;

	@Transient
	private String matchingPassword;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "videogiochi_giocati", joinColumns = @JoinColumn(name = "utente_id"),
	inverseJoinColumns = @JoinColumn(name = "videogioco_id"))
	private Set<Videogioco> videogiochiGiocati;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "videogiochi_desiderati", joinColumns = @JoinColumn(name = "utente_id"),
	inverseJoinColumns = @JoinColumn(name = "videogioco_id"))
	private Set<Videogioco> videogiochiDesiderati;

	@OneToMany(mappedBy = "utente")
	private Set<VideogiocoPiaciuto> videogiochiPiaciuti;

	@OneToMany(mappedBy = "utente")
	private Set<VideogiocoInVendita> videogiochiInVendita;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Utente utente = (Utente) o;
		return Objects.equals(nome, utente.nome) && Objects.equals(cognome, utente.cognome)
				&& Objects.equals(email, utente.email) && Objects.equals(username, utente.username)
				&& Objects.equals(password, utente.password);
	}

	@Override
	public String toString() {
		return "Utente{" + "nome='" + nome + '\'' + ", cognome='" + cognome + '\'' + ", email='" + email + '\''
				+ ", username='" + username + '\'' + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, cognome, email, username, password);
	}

}
