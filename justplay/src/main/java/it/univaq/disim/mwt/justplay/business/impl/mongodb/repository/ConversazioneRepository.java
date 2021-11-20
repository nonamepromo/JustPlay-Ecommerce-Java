package it.univaq.disim.mwt.justplay.business.impl.mongodb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Repository
public interface ConversazioneRepository extends MongoRepository<Conversazione, String> {

	@Query("{'partecipanti':{$all: ?0}}")
	Optional<Conversazione> findByPartecipanti(Set<String> partecipanti);

	Optional<Conversazione> findByIdConversazione(long idConversazione);

	@Query("SELECT c FROM Conversazione c JOIN c.partecipanti p WHERE p = :username")
	List<Conversazione> findByPartecipanti(@Param("username") String username);

	@Query("{'partecipanti':{$all: ?0}}")
	boolean existsByPartecipanti(Set<String> partecipanti);	

	}
