package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

public interface VideogiocoService{
	
	Videogioco findById(Long id) throws BusinessException;
	
	Page<Videogioco> findAll(Pageable pageable) throws BusinessException;
	
	Page<Videogioco> searchVideogioco(String search, int numeroPagine, int sizePagina) throws BusinessException;
	
	//VideogiocoPiaciuto findLikedGame(Utente utente, Videogioco videogioco) throws BusinessException;
	
	//List<VideogiocoInVendita> findAllVendita(Videogioco videogioco) throws BusinessException; // Serve per i giochi in vendit da mostrare in details
	
	void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Utente utente, Videogioco videogioco) throws BusinessException;

	void addGameToLikedlist(Videogioco videogioco, Utente utente, boolean piaciuto) throws BusinessException;

	void removeGameFromSellinglist(Videogioco videogioco, Utente utente, VideogiocoInVendita videogiocoInVendita) throws BusinessException;
	
}