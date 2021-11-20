package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.AmazonRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.GamestopRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.PincodesRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoInVenditaRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoRepository;
import it.univaq.disim.mwt.justplay.domain.Amazon;
import it.univaq.disim.mwt.justplay.domain.GameStop;
import it.univaq.disim.mwt.justplay.domain.Pincodes;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Service
@Transactional
public class VideogiocoServiceImpl implements VideogiocoService {

	@Autowired
	private VideogiocoRepository videogiocoRepository;
	
	@Autowired
	private AmazonRepository amazonRepository;
	
	@Autowired
	private GamestopRepository gamestopRepository;
	
	@Autowired
	private PincodesRepository pincodesRepository;

	@Autowired
	private VideogiocoInVenditaRepository videogiocoInVenditaRepository;

	@Override
	public Videogioco findById(Long id) throws BusinessException {
		return videogiocoRepository.findById(id).get();
	}

	@Override
	public Page<Videogioco> findAll(Pageable pageable) throws BusinessException {

		Page<Videogioco> videogiochiPage = videogiocoRepository.findAll(pageable);

		return videogiochiPage;
	}

	@Override
	public Page<Videogioco> filterVideogiochi(Pageable pageable, String searchString, String platform)
			throws BusinessException {

		Page<Videogioco> videogiochiPage = null;

		switch (platform) {
		case "ps4":
			videogiochiPage = videogiocoRepository.findByTitoloContainingAndPs4(pageable, searchString,
					true);
			break;

		case "xbox":
			videogiochiPage = videogiocoRepository.findByTitoloContainingAndXbox(pageable, searchString,
					true);
			break;

		case "pc":
			videogiochiPage = videogiocoRepository.findByTitoloContainingAndPc(pageable, searchString, true);
			break;
			
		default:
			videogiochiPage = videogiocoRepository.findByTitoloContaining(pageable, searchString);
		}

		return videogiochiPage;
	}

	@Override
	public void addGameToSellinglist(VideogiocoInVendita videogiocoInVendita, Utente utente, Videogioco videogioco)
			throws BusinessException {
		try {
			VideogiocoInVendita newVideogiocoInVendita = new VideogiocoInVendita();
			newVideogiocoInVendita.setUtente(utente);
			newVideogiocoInVendita.setVideogioco(videogioco);
			newVideogiocoInVendita.setPrezzo(videogiocoInVendita.getPrezzo());
			newVideogiocoInVendita.setPrezzoSpedizione(videogiocoInVendita.getPrezzoSpedizione());
			newVideogiocoInVendita.setProvincia(videogiocoInVendita.getProvincia());
			newVideogiocoInVendita.setPiattaforma(videogiocoInVendita.getPiattaforma());
			videogiocoInVenditaRepository.save(newVideogiocoInVendita);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	

	@Override
	public void editSelledGame(VideogiocoInVendita videogiocoInVendita) throws BusinessException {
		try {
			VideogiocoInVendita updateVideogiocoInVendita = videogiocoInVenditaRepository.findById(videogiocoInVendita.getId()).get();
			updateVideogiocoInVendita.setPrezzo(videogiocoInVendita.getPrezzo());
			updateVideogiocoInVendita.setPrezzoSpedizione(videogiocoInVendita.getPrezzoSpedizione());
			updateVideogiocoInVendita.setPiattaforma(videogiocoInVendita.getPiattaforma());
			updateVideogiocoInVendita.setProvincia(videogiocoInVendita.getProvincia());
			videogiocoInVenditaRepository.save(updateVideogiocoInVendita);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void removeGameFromSellinglist(VideogiocoInVendita videogiocoInVendita) throws BusinessException {
		videogiocoInVenditaRepository.deleteById(videogiocoInVendita.getId());
	}

	@Override
	public List<Amazon> findAllByVideogiocoAmazon(Videogioco videogioco) throws BusinessException {
		try {
			return amazonRepository.findAllByVideogioco(videogioco);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<GameStop> findAllByVideogiocoGamestop(Videogioco videogioco) throws BusinessException {
		try {
			return gamestopRepository.findAllByVideogioco(videogioco);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Pincodes> findAllPincodes() throws BusinessException {
		return pincodesRepository.findAll();
	}

}
