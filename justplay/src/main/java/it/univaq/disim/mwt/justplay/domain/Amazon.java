package it.univaq.disim.mwt.justplay.domain;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "amazon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amazon {

	private String titolo;
	
	private String piattaforma;

	private String amazonUrl;

	private double prezzoAmazon;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Amazon amazon = (Amazon) o;
		return Objects.equals(titolo, amazon.titolo) && Objects.equals(piattaforma, amazon.piattaforma) 
				&& Objects.equals(amazonUrl, amazon.amazonUrl)
				&& Objects.equals(prezzoAmazon, amazon.prezzoAmazon);
	}

	@Override
	public int hashCode() {
		return Objects.hash(titolo, piattaforma, amazonUrl, prezzoAmazon);
	}

}