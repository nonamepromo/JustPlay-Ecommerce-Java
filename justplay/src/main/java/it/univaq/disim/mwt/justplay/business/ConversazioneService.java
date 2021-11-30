package it.univaq.disim.mwt.justplay.business;

import java.util.List;
import java.util.Set;

import it.univaq.disim.mwt.justplay.domain.Conversazione;

public interface ConversazioneService {

	Conversazione findConversazioneByUsernames(Set<String> partecipanti) throws BusinessException;

	List<Conversazione> findAllByUsername(String username) throws BusinessException;
	
	void addOrUpdateConversazione(Conversazione conversazione) throws BusinessException;
	
}
