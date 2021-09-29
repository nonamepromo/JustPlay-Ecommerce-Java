package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.MessaggioService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.UtenteServiceImpl;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.ConversazioneRepository;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.MessaggioRepository;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MessaggioServiceImpl implements MessaggioService {

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	public List<Messaggio> findAllByIdConversazione(Long idConversazione) throws BusinessException {
		try {
			return messaggioRepository.findAllByIdConversazione(idConversazione);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void insert(Messaggio messaggio) throws BusinessException {
		try {
			messaggioRepository.save(messaggio);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

}
