package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.MessaggioService;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.MessaggioRepository;
import it.univaq.disim.mwt.justplay.domain.Messaggio;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MessaggioServiceImpl implements MessaggioService{

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	public void inserisci(Messaggio messaggio) throws BusinessException {
		try {
			messaggioRepository.save(messaggio);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	
	
}
