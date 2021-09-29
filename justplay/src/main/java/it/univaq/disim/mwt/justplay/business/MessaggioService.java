package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Messaggio;

public interface MessaggioService {
	
	List<Messaggio> findAllByIdConversazione(Long idConversazione) throws BusinessException;

    void insert(Messaggio messagio) throws BusinessException;
}