package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Commento;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, String> {
	
    void deleteById(Long idCommento);
    
}
