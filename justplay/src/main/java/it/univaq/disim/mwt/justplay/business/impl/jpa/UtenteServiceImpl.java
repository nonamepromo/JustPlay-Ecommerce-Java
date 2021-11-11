package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.UtenteRepository;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

@Service
@Transactional
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Utente> findByUsername(String username) throws BusinessException {
		try {
			return utenteRepository.findByUsername(username);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Optional<Utente> findById(Long id) throws BusinessException {
		try {
			return utenteRepository.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public boolean existsByUsername(String username) throws BusinessException {
		try {
			return utenteRepository.existsByUsername(username);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public boolean existsByEmail(String email) throws BusinessException {
		try {
			return utenteRepository.existsByEmail(email);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void update(Utente utente, Long id) throws BusinessException {
		try {
			Utente updUtente = utenteRepository.findById(id).get();
			updUtente.setUsername(utente.getUsername());
			updUtente.setNome(utente.getNome());
			updUtente.setCognome(utente.getCognome());
			updUtente.setEmail(utente.getEmail());
			utenteRepository.save(updUtente);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public void save(Utente utente) throws BusinessException {
		try {
			utenteRepository.save(utente);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void desiderato(Utente utente, Videogioco videogioco) throws BusinessException {
		try {
			Set<Videogioco> desiderati = utente.getVideogiochiDesiderati();
			if (!desiderati.contains(videogioco)) {
				desiderati.add(videogioco);
				utente.setVideogiochiDesiderati(desiderati);
				utenteRepository.save(utente);
			} else {
				desiderati.remove(videogioco);
				utente.setVideogiochiDesiderati(desiderati);
				utenteRepository.save(utente);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void giocato(Utente utente, Videogioco videogioco) throws BusinessException {
		try {
			Set<Videogioco> giocati = utente.getVideogiochiGiocati();
			if (!giocati.contains(videogioco)) {
				giocati.add(videogioco);
				utente.setVideogiochiGiocati(giocati);
				utenteRepository.save(utente);
			} else {
				giocati.remove(videogioco);
				utente.setVideogiochiGiocati(giocati);
				utenteRepository.save(utente);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
