package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Utente;

public interface UtenteService {
	
	Utente findUtenteByUsername(String username) throws BusinessException;

	void updateProfilo(Utente nuovoProfilo) throws BusinessException;

}
