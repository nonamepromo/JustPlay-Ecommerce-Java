package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.UtenteRepository;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Service
@Transactional
@Slf4j
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

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
	public Utente findUtenteByUsername(String username) throws BusinessException {
		try {
			return utenteRepository.findUtenteByUsername(username).get();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Utente findUtenteById(Long id) throws BusinessException {
		try {
			return utenteRepository.findById(id).get();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void updateProfilo(Utente nuovoProfilo) throws BusinessException {
		try {
			utenteRepository.save(nuovoProfilo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void createUtente(Utente utente) throws BusinessException {
		try {
			utenteRepository.save(utente);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
