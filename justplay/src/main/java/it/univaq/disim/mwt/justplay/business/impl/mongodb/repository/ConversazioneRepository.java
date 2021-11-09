package it.univaq.disim.mwt.justplay.business.impl.mongodb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Conversazione;

@Repository
public interface ConversazioneRepository extends MongoRepository<Conversazione, String>{

	@Query("{'partecipanti':{$all: ?0}}")
	Conversazione findByGivenPartecipanti(List<String> partecipanti);
	
}
