package it.univaq.disim.mwt.justplay.business;

import java.util.Optional;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface UtenteService {

	Optional<Utente> findByUsername(String username) throws BusinessException;

	Optional<Utente> findById(Long id) throws BusinessException;

	boolean existsByUsername(String username) throws BusinessException;

	boolean existsByEmail(String email) throws BusinessException;

	void update(Utente utente, Long id) throws BusinessException;

	void save(Utente utente) throws BusinessException;
	
	void desiderato (Utente utente, Videogioco videogioco) throws BusinessException;
	
	void giocato (Utente utente, Videogioco videogioco) throws BusinessException;
	
}