package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

public interface VideogiocoService {

	int getVideogiochiCount(String platform) throws BusinessException;

	List<Videogioco> findAll(int index) throws BusinessException;

	List<Videogioco> findByPlatform(String platform, int index) throws BusinessException;

	List<Videogioco> findAllProfile() throws BusinessException; //Serve per il profilo utente con i giochi da mostrare nell'area personale
	
	List<VideogiocoInVendita> findAllVendita(Long idVideogioco) throws BusinessException; //Serve per i giochi in vendit da mostrare in details

	List<Long> getWishlist(Long idUtente) throws BusinessException;

	List<Long> getPlayedlist(Long idUtente) throws BusinessException;

	List<Long> getSellinglist(Long idVideogioco) throws BusinessException;

	List<Long> getUtenteSellinglist(Long idUtente) throws BusinessException;

	// ResponseEntity<List<Videogioco>> findAllVideogiochiPaginated() throws BusinessException;

	Videogioco findVideogiocoByID(Long id) throws BusinessException;

	void addGameToWishlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void addGameToPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Long idVideogioco, Long idUtente) throws BusinessException;

	void removeGameFromWishlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void removeGameFromPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void removeGameFromSellinglist(Long idVideogioco, Long idUtente) throws BusinessException;

	// boolean checkIfGameIsDesidered(Long idVideogioco, Long idUtente) throws
	// BusinessException;

	//void deleteVideogioco(Videogioco videogioco) throws BusinessException;

	//void addVideogiocoDesiderato(Videogioco videogioco, Long idUtente) throws BusinessException;

	//void addVideogiocoGiocato(Videogioco videogioco, Long idUtente) throws BusinessException;

	//void addVideogiocoInVendita(Videogioco videogioco, Long idUtente) throws BusinessException;
}
