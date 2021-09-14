package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

public interface ConversazioneService {

	List<Conversazione> findAllByFkUtente(Long fkUtente) throws BusinessException;

	List<Messaggio> findMessaggiByFkConversazione(Long idConversazione) throws BusinessException;	

	void createMessaggio(Long fk_mittente, Long fk_conversazione, String contenuto) throws BusinessException;

	String findNameByIdConversazione(Long idConversazione, Long idUtente) throws BusinessException;
	
	void updateConversazione(Long fk_utente1, Long fk_utente2) throws BusinessException;

}