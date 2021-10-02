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
	
    @Id
    private Long id;

    private Long fkVideogioco;

    private String amazonUrl;
    
    private double prezzoAmazon;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Amazon amazon = (Amazon) o;
        return Objects.equals(id, amazon.id) &&
                Objects.equals(fkVideogioco, amazon.fkVideogioco) &&
                Objects.equals(amazonUrl, amazon.amazonUrl) &&
        		Objects.equals(prezzoAmazon, amazon.prezzoAmazon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fkVideogioco, amazonUrl, prezzoAmazon);
    }
    
}