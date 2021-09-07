package it.univaq.disim.mwt.justplay.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.disim.mwt.justplay.presentation.validator.EmailUnique;
import it.univaq.disim.mwt.justplay.presentation.validator.OnCreate;
import it.univaq.disim.mwt.justplay.presentation.validator.OnUpdate;
import it.univaq.disim.mwt.justplay.presentation.validator.UsernameUnique;
import it.univaq.disim.mwt.justplay.security.PasswordMatches;
import it.univaq.disim.mwt.justplay.security.ValidEmail;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyGroup;

@Entity
@Table(name = "utenti")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@PasswordMatches
public class Utente extends AbstractPersistableEntity {

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String nome;

	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	private String cognome;

	@EmailUnique(groups = { OnCreate.class })
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Email(groups = { OnCreate.class, OnUpdate.class, Default.class })
	@Size(min = 3, max = 45, groups = { OnCreate.class, OnUpdate.class, Default.class })
	@Column(unique = true)
	private String email;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascita;

	@UsernameUnique(groups = { OnCreate.class })
	@NotEmpty(groups = { OnCreate.class, Default.class })
	@Size(min = 3, max = 25, groups = { OnCreate.class, OnUpdate.class, Default.class })
	@Column(unique = true)
	private String username;

	@JsonIgnore
	@NotEmpty(groups = { OnCreate.class, Default.class })
	private String password;

	@NotNull
	@Column(name = "tipologia_utente", columnDefinition = "integer default 2")
	private int tipologia;

	private String matchingPassword;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Utente utente = (Utente) o;
		return Objects.equals(nome, utente.nome) && Objects.equals(cognome, utente.cognome)
				&& Objects.equals(email, utente.email) && Objects.equals(username, utente.username)
				&& Objects.equals(dataNascita, utente.dataNascita) && Objects.equals(password, utente.password);
		// Objects.equals(getRole(), user.getRole());
	}

	@Override
	public String toString() {
		return "Utente{" + "nome='" + nome + '\'' + ", cognome='" + cognome + '\'' + ", email='" + email + '\''
				+ ", username='" + username + '\'' + ", dataNascita=" + dataNascita +
				// ", role='" + getRole() + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, cognome, email, username, dataNascita, password/* ,getRole() */);
	}

	// CAPIRE BENE COSA FARE CON I RUOLI@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// private Set<Ruolo> ruoli = new HashSet<>();
}
