package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;

public interface VideogiocoService {

	List<Videogioco> findAll() throws BusinessException;

	List<Long> getWishlist(Long idUtente) throws BusinessException;
	
	ResponseEntity<List<Videogioco>> findAllVideogiochiPaginated() throws BusinessException;

	Videogioco findVideogiocoByID(Long id) throws BusinessException;	

	void addGameToWishlist(Long idVideogioco, Long idUtente) throws BusinessException;
	
	void removeGameFromWishlist(Long idVideogioco, Long idUtente) throws BusinessException;

	boolean checkIfGameIsDesidered(Long idVideogioco, Long idUtente) throws BusinessException;
	
	void deleteVideogioco(Videogioco videogioco) throws BusinessException;

	void addVideogiocoDesiderato(Videogioco videogioco, Long idUtente) throws BusinessException;
	
	void addVideogiocoInVendita(Videogioco videogioco, Long idUtente) throws BusinessException;
}
