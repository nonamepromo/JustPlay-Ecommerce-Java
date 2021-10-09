package it.univaq.disim.mwt.justplay.domain;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "messaggi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Messaggio {

	private Long idMittente;

	private Long idConversazione;

    private String contenuto;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messaggio messaggio = (Messaggio) o;
        return  Objects.equals(idMittente, messaggio.idMittente) &&
                Objects.equals(idConversazione, messaggio.idConversazione) &&
                Objects.equals(contenuto, messaggio.contenuto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMittente, idConversazione, contenuto);
    }

}
