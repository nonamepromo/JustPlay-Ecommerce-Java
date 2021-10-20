package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

public interface VideogiocoService {

	int getVideogiochiCount(String platform) throws BusinessException;

	int getVideogiochiSearchedCount(String searchString) throws BusinessException;

	List<Videogioco> findAll(int index) throws BusinessException;

	List<Videogioco> findByPlatform(String platform, int index) throws BusinessException;

	List<Videogioco> findByPlatformResearched(String platform, int index, String searchString) throws BusinessException;

	List<Videogioco> findAllProfile() throws BusinessException; //Serve per il profilo utente con i giochi da mostrare nell'area personale
	
	List<VideogiocoInVendita> findAllVendita(Long idVideogioco) throws BusinessException; //Serve per i giochi in vendit da mostrare in details

	List<Long> getWishlist(Long idUtente) throws BusinessException;

	List<Long> getPlayedlist(Long idUtente) throws BusinessException;

	List<Long> getSellinglist(Long idVideogioco) throws BusinessException;

	List<Long> getUtenteSellinglist(Long idUtente) throws BusinessException;
	
	//List<Long> getLikedList(Long idUtente) throws BusinessException; //PER VIDEOGIOCHI PIACIUTI 163
	
	VideogiocoPiaciuto findLikedGame(Long idUtente, Long idVideogioco) throws BusinessException; //PER TROVARE UN SOLO VIDEOGIOCO

	Videogioco findVideogiocoByID(Long id) throws BusinessException;

	void addGameToWishlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void addGameToPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Long idVideogioco, Long idUtente) throws BusinessException;
	
	void addGameToLikedlist(Long idVideogioco, Long idUtente, boolean piaciuto) throws BusinessException; //PER VIDEOGIOCHI PIACIUTI 199

	void removeGameFromWishlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void removeGameFromPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException;

	void removeGameFromSellinglist(Long idVideogioco, Long idUtente) throws BusinessException;
	
	void removeGameFromLikedlist(Long idVideogioco, Long idUtente) throws BusinessException;
	
	void popolamentazione() throws BusinessException;
	
	
}
