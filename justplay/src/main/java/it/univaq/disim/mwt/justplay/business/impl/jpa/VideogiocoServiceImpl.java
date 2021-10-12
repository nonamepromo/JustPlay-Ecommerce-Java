package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoDesideratoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoGiocatoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoInVenditaRepository;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoGiocato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Service
@Transactional
@Slf4j
public class VideogiocoServiceImpl implements VideogiocoService {

	@Autowired
	private VideogiocoRepository videogiocoRepository;

	@Autowired
	private VideogiocoInVenditaRepository videogiocoInVenditaRepository;
	
	@Autowired
	private VideogiocoGiocatoRepository videogiocoGiocatoRepository;
	
	@Autowired
	private VideogiocoDesideratoRepository videogiocoDesideratoRepository;

	@Override
	public int getVideogiochiCount(String platform) throws BusinessException {
		int videogiochiCount = 0;
		switch(platform) {
			case "all": videogiochiCount = (int)videogiocoRepository.count();
			break;
			case "ps4": videogiochiCount = videogiocoRepository.countByPs4UrlNotNull();
			break;
			case "xbox": videogiochiCount = videogiocoRepository.countByXboxUrlNotNull();
			break;
			case "pc": videogiochiCount = videogiocoRepository.countByPcUrlNotNull();
			break;
		}
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
		switch(platform) {
			case "all": videogiochi = videogiocoRepository.findBy(pageWithThreeElements);
			break;
			case "ps4": videogiochi = videogiocoRepository.findAllByPs4UrlNotNull(pageWithThreeElements);
			break;
			case "xbox": videogiochi = videogiocoRepository.findAllByXboxUrlNotNull(pageWithThreeElements);
			break;
			case "pc": videogiochi = videogiocoRepository.findAllByPcUrlNotNull(pageWithThreeElements);
			break;
		}
		return videogiochi;
	}

	@Override
	public List<Videogioco> findByPlatformResearched(String platform, int index, String searchString) throws BusinessException {
		List<Videogioco> videogiochi = new ArrayList<>();
		Pageable pageWithThreeElements = PageRequest.of(index - 1, 3);
		switch(platform) {
			case "all": videogiochi = videogiocoRepository.findByTitoloContaining(searchString);
			break;
			case "ps4": videogiochi = videogiocoRepository.findAllByPs4UrlNotNull(pageWithThreeElements);
			break;
			case "xbox": videogiochi = videogiocoRepository.findAllByXboxUrlNotNull(pageWithThreeElements);
			break;
			case "pc": videogiochi = videogiocoRepository.findAllByPcUrlNotNull(pageWithThreeElements);
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

	@Override
	public Videogioco findVideogiocoByID(Long id) throws BusinessException {
		Videogioco videogioco = videogiocoRepository.findById(id).get();
		return videogioco;
	}

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

	@Override
	public void removeGameFromWishlist(Long idVideogioco, Long idUtente) throws BusinessException {
		videogiocoDesideratoRepository.deleteByFkVideogiocoAndFkUtente(idVideogioco, idUtente);
	}

	@Override
	public void removeGameFromPlayedlist(Long idVideogioco, Long idUtente) throws BusinessException {
		videogiocoGiocatoRepository.deleteByFkVideogiocoAndFkUtente(idVideogioco, idUtente);		
	}

	@Override
	public void removeGameFromSellinglist(Long idVideogioco, Long idUtente) throws BusinessException {
		videogiocoInVenditaRepository.deleteByFkVideogiocoAndFkUtente(idVideogioco, idUtente);
		
	}


	// @Override
	// public boolean existsByUsername(String username) throws BusinessException {
	// 	try {
	// 		return videogiocoRepository.existsByUsername(username);
	// 	} catch (Exception e) {
	// 		throw new BusinessException(e);
	// 	}
	// }

	// @Override
	// public boolean existsByEmail(String email) throws BusinessException {
	// 	try {
	// 		return utenteRepository.existsByEmail(email);
	// 	} catch (Exception e) {
	// 		throw new BusinessException(e);
	// 	}
	// }

	// @Override
	// public Optional<Utente> findByUsername(String username) throws BusinessException {
	// 	try {
	// 		return utenteRepository.findByUsername(username);
	// 	} catch (Exception e) {
	// 		throw new BusinessException(e);
	// 	}
	// }

	// @Override
	// public Optional<Utente> findById(Long id) throws BusinessException {
	// 	try {
	// 		return utenteRepository.findById(id);
	// 	} catch (Exception e) {
	// 		throw new BusinessException(e);
	// 	}
	// }

	// @Override
	// public void save(Utente nuovoProfilo, Long id) throws BusinessException {
	// 	try {
	// 		Utente updUtente = utenteRepository.findById(id).get();
	// 		updUtente.setUsername(nuovoProfilo.getUsername());
	// 		updUtente.setNome(nuovoProfilo.getNome());
	// 		updUtente.setCognome(nuovoProfilo.getCognome());
	// 		updUtente.setEmail(nuovoProfilo.getEmail());
	// 		utenteRepository.save(updUtente);
	// 	} catch (Exception e) {
	// 		throw new BusinessException(e);
	// 	}
	// }

	// @Override
	// public void save(Utente utente) throws BusinessException {
	// 	if (!passwordEncoder.matches(utente.getPassword(), passwordEncoder.encode(utente.getMatchingPassword()))) {
	// 		throw new BusinessException("Le due password non coincidono");
	// 	}
	// 	try {
	// 		utente.setPassword(passwordEncoder.encode(utente.getPassword()));
	// 		utenteRepository.save(utente);
	// 	} catch (Exception e) {
	// 		throw new BusinessException(e);
	// 	}
	// }

}
