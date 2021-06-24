package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Conversazione;

public interface ConversazioneService {

	List<Conversazione> findAllByFkUtente(Long fk_utente) throws BusinessException;

	Conversazione findConversazioneByFkUtenti(Long fk_utente1, Long fk_utente2) throws BusinessException;	

	void createConversazione(Long fk_utente1, Long fk_utente2, String firstMessage) throws BusinessException;
	
	void updateConversazione(Long fk_utente1, Long fk_utente2, String newMessage) throws BusinessException;

}