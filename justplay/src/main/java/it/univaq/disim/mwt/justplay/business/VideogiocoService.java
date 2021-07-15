package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

public interface VideogiocoService {

	List<Videogioco> findAll(int index) throws BusinessException;

	List<Videogioco> findAllProfile() throws BusinessException;

	List<Long> getWishlist(Long idUtente) throws BusinessException;

	List<Long> getPlayedlist(Long idUtente) throws BusinessException;

	List<Long> getSellinglist(Long idUtente) throws BusinessException;

	ResponseEntity<List<Videogioco>> findAllVideogiochiPaginated() throws BusinessException;

	Videogioco findVideogiocoByID(Long id) throws BusinessException;

	void addGameToWishlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void addGameToPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void addGameToSellinglist(Long idVideogioco, Long idUtente) throws BusinessException;

	void addGameToSellinglistProva(VideogiocoInVendita videogiocoInVendita) throws BusinessException;

	void removeGameFromWishlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void removeGameFromPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void removeGameFromSellinglist(Long idVideogioco, Long idUtente) throws BusinessException;

	// boolean checkIfGameIsDesidered(Long idVideogioco, Long idUtente) throws
	// BusinessException;

	void deleteVideogioco(Videogioco videogioco) throws BusinessException;

	void addVideogiocoDesiderato(Videogioco videogioco, Long idUtente) throws BusinessException;

	void addVideogiocoGiocato(Videogioco videogioco, Long idUtente) throws BusinessException;

	void addVideogiocoInVendita(Videogioco videogioco, Long idUtente) throws BusinessException;
}
