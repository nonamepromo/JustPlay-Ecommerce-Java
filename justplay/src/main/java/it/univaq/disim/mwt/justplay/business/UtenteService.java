package it.univaq.disim.mwt.justplay.business;

import java.util.Optional;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface UtenteService {

	Optional<Utente> findByUsername(String username) throws BusinessException;

	Optional<Utente> findById(Long id) throws BusinessException;
	
	boolean existsByUsername(String username) throws BusinessException;

	boolean existsByEmail(String email) throws BusinessException;

	void update(Utente utente) throws BusinessException;

	void save(Utente utente) throws BusinessException;
	
	void addOrRemoveDesiderato (Utente utente, Videogioco videogioco) throws BusinessException;
	
	void addOrRemoveGiocato (Utente utente, Videogioco videogioco) throws BusinessException;
	
	void piaciuto(Utente utente, Videogioco videogioco) throws BusinessException;
	
	void nonPiaciuto(Utente utente, Videogioco videogioco) throws BusinessException;
	
}