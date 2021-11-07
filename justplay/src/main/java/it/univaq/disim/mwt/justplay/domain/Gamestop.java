package it.univaq.disim.mwt.justplay.domain;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "gamestop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gamestop {

	private String titolo;
	
	private String piattaforma;

	private String gamestopUrl;

	private double prezzoGamestop;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Gamestop gamestop = (Gamestop) o;
		return Objects.equals(titolo, gamestop.titolo) && Objects.equals(piattaforma, gamestop.piattaforma)
				&& Objects.equals(gamestopUrl, gamestop.gamestopUrl)
				&& Objects.equals(prezzoGamestop, gamestop.prezzoGamestop);
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo, piattaforma, gamestopUrl, prezzoGamestop);
	}

}
