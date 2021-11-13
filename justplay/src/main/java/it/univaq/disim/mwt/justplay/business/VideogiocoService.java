package it.univaq.disim.mwt.justplay.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

public interface VideogiocoService{
	
	Videogioco findById(Long id) throws BusinessException;
	
	Page<Videogioco> findAll(Pageable pageable) throws BusinessException;
	
	Page<Videogioco> filterVideogiochi(Pageable pageable, String searchString, String platform) throws BusinessException;
		
	void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Utente utente, Videogioco videogioco) throws BusinessException;

	void addGameToLikedlist(Videogioco videogioco, Utente utente, boolean piaciuto) throws BusinessException;

	void removeGameFromSellinglist(Utente utente, Long videogiocoInVendita) throws BusinessException;
	
}