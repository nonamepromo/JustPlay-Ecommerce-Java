package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.UtenteRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoInVenditaRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoPiaciutoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoRepository;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

@Service
@Transactional
public class VideogiocoServiceImpl implements VideogiocoService {

	@Autowired
	private VideogiocoPiaciutoRepository videogiocoPiaciutoRepository;

	@Autowired
	private VideogiocoRepository videogiocoRepository;

	@Autowired
	private UtenteRepository utenteRepository;

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
	public void removeGameFromSellinglist(Utente utente, Long videogiocoInVendita) throws BusinessException {
		videogiocoInVenditaRepository.deleteByUtenteAndId(utente, videogiocoInVendita);
	}

	@Override
	public void addGameToLikedlist(Videogioco videogioco, Utente utente, boolean piaciuto) throws BusinessException {
		try {
			Set<VideogiocoPiaciuto> videogiochiPiaciuti = utente.getVideogiochiPiaciuti();
			if (videogiochiPiaciuti.contains(videogioco)) {
				VideogiocoPiaciuto videogiocoPiaciuto = videogiocoPiaciutoRepository.findByUtenteAndVideogioco(utente,
						videogioco);
				if (videogiocoPiaciuto.isPiaciuto() == piaciuto) {
					videogiochiPiaciuti.remove(videogiocoPiaciuto);
				} else {
					videogiochiPiaciuti.remove(videogiocoPiaciuto);
					videogiocoPiaciuto.setPiaciuto(piaciuto);
					videogiocoPiaciuto.setUtente(utente);
					videogiocoPiaciuto.setVideogioco(videogioco);
					videogiocoPiaciutoRepository.save(videogiocoPiaciuto);
				}
			} else {
				VideogiocoPiaciuto videogiocoPiaciuto = new VideogiocoPiaciuto();
				videogiocoPiaciuto.setPiaciuto(piaciuto);
				videogiocoPiaciuto.setUtente(utente);
				videogiocoPiaciuto.setVideogioco(videogioco);
				videogiocoPiaciutoRepository.save(videogiocoPiaciuto);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
