package it.univaq.disim.mwt.justplay.domain;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "mdbgames")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideogiochiMongoDB {
	
	private Long idVideogioco;
	
	private String titolo;
	
	private int anno_di_uscita;
	
	private String descrizione;
	
	private String descrizioneEN;
	
	private String image_url;
	
	private String pc_url;
	
	private String ps4_url;
	
	private String xbox_url;
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VideogiochiMongoDB videogiochiMongoDB = (VideogiochiMongoDB) o;
		return Objects.equals(idVideogioco, videogiochiMongoDB.idVideogioco)
				&& Objects.equals(titolo, videogiochiMongoDB.titolo)
				&& Objects.equals(anno_di_uscita, videogiochiMongoDB.anno_di_uscita)
				&& Objects.equals(descrizione, videogiochiMongoDB.descrizione)
				&& Objects.equals(descrizioneEN, videogiochiMongoDB.descrizioneEN)
				&& Objects.equals(image_url, videogiochiMongoDB.image_url)
				&& Objects.equals(pc_url, videogiochiMongoDB.pc_url)
				&& Objects.equals(ps4_url, videogiochiMongoDB.ps4_url)
				&& Objects.equals(xbox_url, videogiochiMongoDB.xbox_url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idVideogioco, titolo, anno_di_uscita, descrizione, descrizioneEN, image_url, pc_url, ps4_url, xbox_url);
	}
	
}
