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

	private Long fkVideogioco;

	private String amazonUrl;

	private double prezzoAmazon;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Amazon amazon = (Amazon) o;
		return Objects.equals(fkVideogioco, amazon.fkVideogioco) && Objects.equals(amazonUrl, amazon.amazonUrl)
				&& Objects.equals(prezzoAmazon, amazon.prezzoAmazon);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fkVideogioco, amazonUrl, prezzoAmazon);
	}

}