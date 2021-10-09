package it.univaq.disim.mwt.justplay.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "conversazioni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conversazione {
	
	private Long idConversazione;

	private Long fkUtente1;

	private Long fkUtente2;

	private String nomeUtente1;

	private String nomeUtente2;
	
	private Date data;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversazione conversazione = (Conversazione) o;
        return  Objects.equals(idConversazione, conversazione.idConversazione) &&
        		Objects.equals(fkUtente1, conversazione.fkUtente1) &&
                Objects.equals(fkUtente2, conversazione.fkUtente2) &&
                Objects.equals(nomeUtente1, conversazione.nomeUtente1) &&
                Objects.equals(nomeUtente2, conversazione.nomeUtente2) &&
                Objects.equals(data, conversazione.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConversazione, fkUtente1, fkUtente2, nomeUtente1, nomeUtente2, data);
    }
	
}
