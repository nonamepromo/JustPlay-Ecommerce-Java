package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
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
	private VideogiocoInVenditaRepository videogiocoInVenditaRepository;

	@Override
	public Videogioco findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Videogioco> findAll(Pageable pageable) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Videogioco> searchVideogioco(String search, int numeroPagine, int sizePagina) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
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
	public void removeGameFromSellinglist(Videogioco videogioco, Utente utente, VideogiocoInVendita videogiocoInVendita)
			throws BusinessException {
		videogiocoInVenditaRepository.deleteByVideogiocoAndUtenteAndId(videogioco, utente, videogiocoInVendita.getId());
	}	

	@Override
	public void addGameToLikedlist(Videogioco videogioco, Utente utente, boolean piaciuto) throws BusinessException {
		try {
			Set<VideogiocoPiaciuto> videogiochiPiaciuti = utente.getVideogiochiPiaciuti();
			if(videogiochiPiaciuti.contains(videogioco)) {
				VideogiocoPiaciuto videogiocoPiaciuto = videogiocoPiaciutoRepository.findByUtenteAndVideogioco(utente, videogioco);
				if(videogiocoPiaciuto.isPiaciuto() == piaciuto) {
					videogiochiPiaciuti.remove(videogiocoPiaciuto);
				}else {
					videogiochiPiaciuti.remove(videogiocoPiaciuto);
					videogiocoPiaciuto.setPiaciuto(piaciuto);
					videogiocoPiaciuto.setUtente(utente);
					videogiocoPiaciuto.setVideogioco(videogioco);
					videogiocoPiaciutoRepository.save(videogiocoPiaciuto);
				}
			}else {
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

