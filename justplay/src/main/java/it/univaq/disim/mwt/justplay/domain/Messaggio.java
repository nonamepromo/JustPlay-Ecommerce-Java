package it.univaq.disim.mwt.justplay.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Objects;

@Document(collection = "messaggi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Messaggio {

	@Id
	private String id;

	private String mittente;

	private String destinatario;

	private String contenuto;

	private Long timestamp;

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messaggio messaggio = (Messaggio) o;
        return Objects.equals(id, messaggio.id) &&
                Objects.equals(mittente, messaggio.mittente) &&
                Objects.equals(destinatario, messaggio.destinatario) &&
                Objects.equals(contenuto, messaggio.contenuto) &&
                Objects.equals(timestamp, messaggio.timestamp);
    }

	@Override
    public int hashCode() {
        return Objects.hash(id, mittente, destinatario, contenuto, timestamp);
    }
}
