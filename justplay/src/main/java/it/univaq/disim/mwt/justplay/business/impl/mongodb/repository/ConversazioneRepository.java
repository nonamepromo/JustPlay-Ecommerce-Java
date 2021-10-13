package it.univaq.disim.mwt.justplay.business.impl.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

@Repository
public interface ConversazioneRepository extends MongoRepository<Conversazione, String> {

	List<Conversazione> findAllByFkUtente1(Long fkUtente1) throws BusinessException;

	List<Conversazione> findAllByFkUtente2(Long fkUtente2) throws BusinessException;
	
	Conversazione findByFkUtente1AndFkUtente2(Long fkUtente1, Long fkUtente2) throws BusinessException;
	
	Conversazione findNameByIdConversazione(Long idConversazione, Long idUtente) throws BusinessException;

}
