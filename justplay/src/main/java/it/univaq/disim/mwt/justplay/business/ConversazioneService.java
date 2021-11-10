package it.univaq.disim.mwt.justplay.business;

import java.util.List;
import java.util.Optional;

import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

public interface ConversazioneService {
	
	Conversazione inserisci (Conversazione conversazione) throws BusinessException;
	
	void save(Conversazione conversazione) throws BusinessException;
	
	Optional<Conversazione>  findByPartecipanti(List<String> partecipanti) throws BusinessException;
	
	void invia (Messaggio messaggio) throws BusinessException;
	
}
