package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.univaq.disim.mwt.justplay.domain.Pincodes;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

public interface VideogiocoService{
	
	Videogioco findById(Long id) throws BusinessException;
				
	Page<Videogioco> filterVideogiochi(Pageable pageable, String searchString, String platform) throws BusinessException;
		
	void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Utente utente, Videogioco videogioco) throws BusinessException;

	void removeGameFromSellinglist(VideogiocoInVendita videogiocoInVendita) throws BusinessException;
	
	void editSelledGame(VideogiocoInVendita videogiocoInVendita) throws BusinessException;
	
	List<Pincodes> findAllPincodes() throws BusinessException;

}