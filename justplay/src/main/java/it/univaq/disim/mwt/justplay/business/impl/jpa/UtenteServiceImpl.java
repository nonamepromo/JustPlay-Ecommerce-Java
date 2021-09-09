package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

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
	public void update(Utente nuovoProfilo, Long id) throws BusinessException {
		try {
			Utente updUtente = utenteRepository.findById(id).get(); 
			updUtente.setUsername(nuovoProfilo.getUsername()); 
			updUtente.setNome(nuovoProfilo.getNome()); 
			updUtente.setCognome(nuovoProfilo.getCognome()); 
			utenteRepository.save(updUtente);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void save(Utente utente) throws BusinessException {
		if (!passwordEncoder.matches(utente.getPassword(), passwordEncoder.encode(utente.getMatchingPassword()))) {
			throw new BusinessException("Le due password non coincidono");
		}
		try {
			utente.setPassword(passwordEncoder.encode(utente.getPassword()));
			utenteRepository.save(utente);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
