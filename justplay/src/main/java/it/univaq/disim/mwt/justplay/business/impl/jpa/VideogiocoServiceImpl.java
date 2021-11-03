package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoDesideratoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoGiocatoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoInVenditaRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoPiaciutoRepository;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoGiocato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;
import it.univaq.disim.mwt.justplay.presentation.Utility;

@Service
@Transactional
public class VideogiocoServiceImpl implements VideogiocoService {

	@Autowired
	private VideogiocoRepository videogiocoRepository;

	@Autowired
	private VideogiocoInVenditaRepository videogiocoInVenditaRepository;

	@Autowired
	private VideogiocoGiocatoRepository videogiocoGiocatoRepository;

	@Autowired
	private VideogiocoDesideratoRepository videogiocoDesideratoRepository;

	@Autowired
	private VideogiocoPiaciutoRepository videogiocoPiaciutoRepository;

	@Override
	public int getVideogiochiCount(String platform) throws BusinessException {
		int videogiochiCount = 0;
		switch (platform) {
		case "all":
			videogiochiCount = (int) videogiocoRepository.count();
			break;
		case "ps4":
			videogiochiCount = videogiocoRepository.countByPs4UrlNotNull();
			break;
		case "xbox":
			videogiochiCount = videogiocoRepository.countByXboxUrlNotNull();
			break;
		case "pc":
			videogiochiCount = videogiocoRepository.countByPcUrlNotNull();
			break;
		}
		return videogiochiCount;
	}

	@Override
	public int getVideogiochiSearchedCount(String searchString) throws BusinessException {
		int videogiochiCount = 0;
		videogiochiCount = (int) videogiocoRepository.countByTitoloContaining(searchString);
		return videogiochiCount;
	}

	@Override
	public List<Videogioco> findAll(int index) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Videogioco> findByPlatform(String platform, int index) throws BusinessException {
		List<Videogioco> videogiochi = new ArrayList<>();
		Pageable pageWithThreeElements = PageRequest.of(index - 1, 3);
		switch (platform) {
		case "all":
			videogiochi = videogiocoRepository.findBy(pageWithThreeElements);
			break;
		case "ps4":
			videogiochi = videogiocoRepository.findAllByPs4UrlNotNull(pageWithThreeElements);
			break;
		case "xbox":
			videogiochi = videogiocoRepository.findAllByXboxUrlNotNull(pageWithThreeElements);
			break;
		case "pc":
			videogiochi = videogiocoRepository.findAllByPcUrlNotNull(pageWithThreeElements);
			break;
		}
		return videogiochi;
	}

	@Override
	public List<Videogioco> findByPlatformResearched(String platform, int index, String searchString)
			throws BusinessException {
		List<Videogioco> videogiochi = new ArrayList<>();
		Pageable pageWithThreeElements = PageRequest.of(index - 1, 3);
		Pageable page = PageRequest.of(1, 3);
		switch (platform) {
		case "all":
			videogiochi = videogiocoRepository.findByTitoloContaining(searchString, pageWithThreeElements);
			break;
		case "ps4":
			videogiochi = videogiocoRepository.findAllByPs4UrlNotNull(pageWithThreeElements);
			break;
		case "xbox":
			videogiochi = videogiocoRepository.findAllByXboxUrlNotNull(pageWithThreeElements);
			break;
		case "pc":
			videogiochi = videogiocoRepository.findAllByPcUrlNotNull(pageWithThreeElements);
			break;
		}
		return videogiochi;
	}

	@Override
	public List<Videogioco> findAllProfile() throws BusinessException {
		List<Videogioco> videogiochi = new ArrayList<>();
		videogiochi = videogiocoRepository.findAll();
		return videogiochi;
	}

	@Override
	public List<VideogiocoInVendita> findAllVendita(Long idVideogioco) throws BusinessException {
		List<VideogiocoInVendita> videogiochiInVendita = new ArrayList<>();
		videogiochiInVendita = videogiocoInVenditaRepository.findAllByFkVideogioco(idVideogioco);
		return videogiochiInVendita;
	}
/*
	@Override
	public List<Long> getWishlist(Long idUtente) throws BusinessException {
		List<Long> videogiochiDesideratiIds = videogiocoDesideratoRepository.findFksVideogiocoByFkUtente(idUtente);
		return videogiochiDesideratiIds;
	}

	@Override
	public List<Long> getPlayedlist(Long idUtente) throws BusinessException {
		List<Long> videogiochiGiocatiIds = videogiocoGiocatoRepository.findFksVideogiocoByFkUtente(idUtente);
		return videogiochiGiocatiIds;
	}
*/
	@Override
	public List<Videogioco> getWishlist(Utente utente) throws BusinessException {
		List<Videogioco> videogiochiDesideratiIds = videogiocoDesideratoRepository.findAllByUtente(utente.getId());
		return videogiochiDesiderati;
	}

	@Override
	public List<Videogioco> getPlayedlist(Utente utente) throws BusinessException {
		List<Videogioco> videogiochiGiocati = videogiocoGiocatoRepository.findAllByUtente(utente.getId());
		return videogiochiGiocati;
	}
	

	@Override
	public List<Long> getSellinglist(Long idVideogioco) throws BusinessException {
		List<Long> videogiochiInVendita = videogiocoInVenditaRepository.findFksVideogiocoByFkVideogioco(idVideogioco);
		return videogiochiInVendita;
	}

	@Override
	public List<Long> getUtenteSellinglist(Long idUtente) throws BusinessException {
		List<Long> videogiochiInVendita = videogiocoInVenditaRepository.findFksVideogiocoByFkUtente(idUtente);
		return videogiochiInVendita;
	}

	/*
	@Override
	public List<Long> getLikedList(Long idUtente) throws BusinessException {
		List<Long> videogiochiPiaciuti = videogiocoPiaciutoRepository.findFksVideogiocoByFkUtente(idUtente);
		return videogiochiPiaciuti;
	}
    */

	@Override
	public VideogiocoPiaciuto findLikedGame(Utente utente, Videogioco videogioco) throws BusinessException {
		VideogiocoPiaciuto videogiocoPiaciuto = new VideogiocoPiaciuto();
		videogiocoPiaciuto = videogiocoPiaciutoRepository.findByUtenteAndVideogioco(utente, videogioco);
		return videogiocoPiaciuto;
	}
	
	@Override
	public int countLikedGameByVideogioco(Videogioco videogioco, boolean piaciuto) throws BusinessException {
		int contatore = videogiocoPiaciutoRepository.countByVideogiocoAndPiaciuto(videogioco, piaciuto); 
		return contatore;
	}

	@Override
	public Videogioco findVideogiocoByID(Long id) throws BusinessException {
		Videogioco videogioco = videogiocoRepository.findById(id).get();
		return videogioco;
	}
/*
	@Override
	public void addGameToWishlist(Long idVideogioco, Long idUtente) throws BusinessException {
		try {
			VideogiocoDesiderato videogiocoDesiderato = new VideogiocoDesiderato();
			videogiocoDesiderato.setFkUtente(idUtente);
			videogiocoDesiderato.setFkVideogioco(idVideogioco);
			videogiocoDesideratoRepository.save(videogiocoDesiderato);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void addGameToPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException {
		try {
			VideogiocoGiocato videogiocoGiocato = new VideogiocoGiocato();
			videogiocoGiocato.setFkUtente(idUtente);
			videogiocoGiocato.setFkVideogioco(idVideogioco);
			videogiocoGiocatoRepository.save(videogiocoGiocato);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
*/

	@Override
	public void addGameToWishlist(Videogioco videogioco, Utente utente) throws BusinessException {
		try {
			VideogiocoDesiderato videogiocoDesiderato = new VideogiocoDesiderato();
			videogiocoDesiderato.setUtente(Utility.getUtente());
			videogiocoDesiderato.setVideogioco(videogioco);
			videogiocoDesideratoRepository.save(videogiocoDesiderato);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void addGameToPlayedlist(Videogioco videogioco, Utente utente) throws BusinessException {
		try{
			VideogiocoGiocato videogiocoGiocato = new VideogiocoGiocato();
			videogiocoGiocato.setUtente(Utility.getUtente());
			videogiocoGiocato.setVideogioco(videogioco);
			videogiocoGiocatoRepository.save(videogiocoGiocato);
		}catch (Exception e) {
			throw new BusinessException(e);
		}
	}


	@Override
	public void addGameToLikedlist(Videogioco videogioco, boolean piaciuto) throws BusinessException {
		try {
			VideogiocoPiaciuto videogiocoPiaciuto = new VideogiocoPiaciuto();
			videogiocoPiaciuto.setUtente(Utility.getUtente());
			videogiocoPiaciuto.setVideogioco(videogioco);
			videogiocoPiaciuto.setPiaciuto(piaciuto);
			videogiocoPiaciutoRepository.save(videogiocoPiaciuto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Long idVideogioco, Long idUtente)
			throws BusinessException {
		try {
			VideogiocoInVendita newVideogiocoInVendita = new VideogiocoInVendita();
			newVideogiocoInVendita.setFkUtente(idUtente);
			newVideogiocoInVendita.setFkVideogioco(idVideogioco);
			newVideogiocoInVendita.setPrezzo(videogiocoInVendita.getPrezzo());
			newVideogiocoInVendita.setPrezzoSpedizione(videogiocoInVendita.getPrezzoSpedizione());
			newVideogiocoInVendita.setProvincia(videogiocoInVendita.getProvincia());
			newVideogiocoInVendita.setPiattaforma(videogiocoInVendita.getPiattaforma());
			videogiocoInVenditaRepository.save(newVideogiocoInVendita);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
/*
	@Override
	public void removeGameFromWishlist(Long idVideogioco, Long idUtente) throws BusinessException {
		videogiocoDesideratoRepository.deleteByFkVideogiocoAndFkUtente(idVideogioco, idUtente);
	}

	@Override
	public void removeGameFromPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException {
		videogiocoGiocatoRepository.deleteByFkVideogiocoAndFkUtente(idVideogioco, idUtente);
	}
*/

	@Override
	public void removeGameFromWishlist(Videogioco videogioco, Utente utente) throws BusinessException {
		videogiocoDesideratoRepository.deleteByVideogiocoAndUtente(videogioco, utente);
	}

	@Override
	public void removeGameFromPlayedlist(Videogioco videogioco, Utente utente) throws BusinessException {
		videogiocoGiocatoRepository.deleteByVideogiocoAndUtente(videogioco, utente);
	}


	@Override
	public void removeGameFromSellinglist(Long idVideogioco, Long idUtente) throws BusinessException {
		videogiocoInVenditaRepository.deleteByFkVideogiocoAndFkUtente(idVideogioco, idUtente);
	}
	
	@Override
	public void removeGameFromLikedlist(Utente utente, Videogioco videogioco) throws BusinessException {
		videogiocoPiaciutoRepository.deleteByUtenteAndVideogioco(utente, videogioco);
	}
	
	@Override
	public void aggiuntaVideogiochi() throws BusinessException {
		Videogioco videogioco = new Videogioco();
		videogioco.setAnnoDiUscita(2019);
		videogioco.setDescrizione("aaaaaaaaaaa");
		videogioco.setImageUrl("naaaaaaaaaa");
		videogioco.setPcUrl("saaaaaaaaaaaa");
		videogioco.setPs4Url("aaaaaaaaa");
		videogioco.setTitolo("Gears 5");
		videogioco.setXboxUrl("faaaaaaaaaaa");
		videogiocoRepository.save(videogioco);
	}
}
