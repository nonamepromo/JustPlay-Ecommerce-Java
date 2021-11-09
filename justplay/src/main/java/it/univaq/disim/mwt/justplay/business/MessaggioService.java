package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Messaggio;

public interface MessaggioService {
	
	void inserisci (Messaggio messaggio) throws BusinessException;
	
}
