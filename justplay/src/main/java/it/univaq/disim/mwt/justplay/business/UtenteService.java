package it.univaq.disim.mwt.justplay.business;

import java.util.Optional;

import it.univaq.disim.mwt.justplay.domain.Utente;

public interface UtenteService {

	Optional<Utente> findByUsername(String username) throws BusinessException;

	Optional<Utente> findById(Long id) throws BusinessException; // AGGIUNTO PER MODIFICARE UTENTE

	boolean existsByUsername(String username) throws BusinessException;

	boolean existsByEmail(String email) throws BusinessException;

	// void update(Utente nuovoProfilo, Long id) throws BusinessException;

	void save(Utente nuovoProfilo, Long id) throws BusinessException;

	void save(Utente utente) throws BusinessException;
}