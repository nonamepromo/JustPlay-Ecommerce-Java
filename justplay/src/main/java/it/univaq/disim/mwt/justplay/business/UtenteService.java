package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Utente;

public interface UtenteService {

	Utente findUtenteByUsername(String username) throws BusinessException;

	Utente findUtenteById(Long id) throws BusinessException; // AGGIUNTO PER MODIFICARE UTENTE

    boolean existsByUsername(String username) throws BusinessException;

    boolean existsByEmail(String email) throws BusinessException;
    
	void updateProfilo(Utente nuovoProfilo) throws BusinessException;

	void createUtente(Utente utente) throws BusinessException;
}