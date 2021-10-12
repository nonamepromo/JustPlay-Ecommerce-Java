package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.Id;
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

	private Long fkVideogioco;

	private String url;

	private double prezzo;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Gamestop gamestop = (Gamestop) o;
		return Objects.equals(fkVideogioco, gamestop.fkVideogioco) && Objects.equals(url, gamestop.url)
				&& Objects.equals(prezzo, gamestop.prezzo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fkVideogioco, url, prezzo);
	}

}
