package it.univaq.disim.mwt.justplay.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Objects;
import java.util.Set;

@Document(collection = "conversazioni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conversazione {

	@Id
	private String idConversazione;

	@DBRef
	private Set<Messaggio> messaggi;

	private Set<String> partecipanti;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Conversazione conversazione = (Conversazione) o;
		return Objects.equals(idConversazione, conversazione.idConversazione) && Objects.equals(messaggi, conversazione.messaggi)
				&& Objects.equals(partecipanti, conversazione.partecipanti);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idConversazione, messaggi, partecipanti);
	}

}