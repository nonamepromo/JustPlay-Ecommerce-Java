package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

public interface ConversazioneService {

	List<Conversazione> findAllByFkUtente(Long idUtente) throws BusinessException;

	//List<Messaggio> findAllByIdConversazione(Long idConversazione) throws BusinessException;

	Conversazione findNameByIdConversazione(Long idConversazione, Long idUtente) throws BusinessException;
	
	void createConversazione(Long fkUtente1, Long fkUtente2) throws BusinessException;
		
	void createMessaggio(Long idMittente, Long idConversazione, String contenuto) throws BusinessException;
	
	void updateConversazione(Long fkUtente1, Long fkUtente2) throws BusinessException;

}