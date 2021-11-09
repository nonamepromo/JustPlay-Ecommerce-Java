package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface UtenteService {

	Utente findByUsername(String username) throws BusinessException;

	Utente findById(Long id) throws BusinessException; // AGGIUNTO PER MODIFICARE UTENTE

	boolean existsByUsername(String username) throws BusinessException;

	boolean existsByEmail(String email) throws BusinessException;

	void save(Utente nuovoProfilo, Long id) throws BusinessException;

	void save(Utente utente) throws BusinessException;
	
	void desiderato (Utente utente, Videogioco videogioco) throws BusinessException;
	
	void giocato (Utente utente, Videogioco videogioco) throws BusinessException;
	
}