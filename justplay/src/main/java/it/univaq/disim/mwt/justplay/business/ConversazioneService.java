package it.univaq.disim.mwt.justplay.business;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

public interface ConversazioneService {
	
	Conversazione findById(Long idConversazione) throws BusinessException;

	Conversazione findConversazioneByUsernames(Set<String> partecipanti) throws BusinessException;
	
	Conversazione inserisci(Conversazione conversazione) throws BusinessException;
	
	List<Conversazione> findAll() throws BusinessException;

	List<Conversazione> findAllByUsername(String username) throws BusinessException;
	
	void addOrUpdateConversazione(Conversazione conversazione) throws BusinessException;
	
	Optional<Conversazione> findByPartecipanti(List<String> partecipanti) throws BusinessException;
	
	void invia(Messaggio messaggio) throws BusinessException;
	
}
