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

@Document(collection = "amazon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amazon{
    @id
    private Long id;

    private Long idVideogioco;

    private String amazonUrl;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Amazon amazon = (Amazon) o;
        return Objects.equals(id, amazon.id) &&
                Objects.equals(idVideogioco, amazon.idVideogioco) &&
                Objects.equals(amazonUrl, amazon.amazonUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idVideogioco, amazonUrl);
    }
    
}