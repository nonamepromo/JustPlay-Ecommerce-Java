package it.univaq.disim.mwt.justplay.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

public interface VideogiocoService{
	
	Videogioco findById(Long id) throws BusinessException;
	
	Page<Videogioco> findAll(Pageable pageable) throws BusinessException;
		
	void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Videogioco videogioco, Utente  utente) throws BusinessException;

	void removeGameFromSellinglist(Videogioco videogioco, Utente utente, VideogiocoInVendita videogiocoInVendita) throws BusinessException;

	void removeGameFromLikedlist(Utente utente, Videogioco videogioco) throws BusinessException;
	
}