package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoGiocato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

public interface VideogiocoService {

	int getVideogiochiCount(String platform) throws BusinessException;

	int getVideogiochiSearchedCount(String searchString) throws BusinessException;

	List<Videogioco> findAll(int index) throws BusinessException;

	List<Videogioco> findByPlatform(String platform, int index) throws BusinessException;

	List<Videogioco> findByPlatformResearched(String platform, int index, String searchString) throws BusinessException;

	List<Videogioco> findAllProfile() throws BusinessException; // Serve per il profilo utente con i giochi da mostrare nell'area personale

	List<VideogiocoInVendita> findAllVendita(Long idVideogioco) throws BusinessException; // Serve per i giochi in vendit da mostrare in details

	List<Videogioco> getWishlist(Utente utente) throws BusinessException;

	List<Videogioco> getPlayedlist(Utente utente) throws BusinessException;

	List<Long> getSellinglist(Long idVideogioco) throws BusinessException;
	
	List<VideogiocoInVendita> getCompleteSellinglist(Long idVideogioco, Long idUtente) throws BusinessException;

	List<Long> getUtenteSellinglist(Long idUtente) throws BusinessException;

	VideogiocoPiaciuto findLikedGame(Utente utente, Videogioco videogioco) throws BusinessException;

	Videogioco findVideogiocoByID(Long id) throws BusinessException;

	int countLikedGameByVideogioco(Videogioco videogioco, boolean piaciuto) throws BusinessException;

	void addGameToWishlist(Videogioco videogioco, Utente utente) throws BusinessException;

	void addGameToPlayedlist(Videogioco videogioco, Utente utente) throws BusinessException;

	void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Long idVideogioco, Long idUtente)
			throws BusinessException;

	void addGameToLikedlist(Videogioco videogioco, boolean piaciuto) throws BusinessException;

	void removeGameFromWishlist(Videogioco videogioco, Utente utente) throws BusinessException;

	void removeGameFromPlayedlist(Videogioco videogioco, Utente utente) throws BusinessException;

	void removeGameFromSellinglist(Long idVideogioco, Long idUtente, Long idVenduto) throws BusinessException;

	void removeGameFromLikedlist(Utente utente, Videogioco videogioco) throws BusinessException;

	VideogiocoInVendita findVideogiocoInVenditaByID(Long id) throws BusinessException;
	
	void saveSellingGame(VideogiocoInVendita videogiocoInVendita) throws BusinessException;
	
	void addGameFromMdb() throws BusinessException;

}
