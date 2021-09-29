package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.MessaggioService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.UtenteServiceImpl;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.ConversazioneRepository;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.MessaggioRepository;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.presentation.Utility;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ConversazioneServiceImpl implements ConversazioneService {
	
	@Autowired
	private ConversazioneRepository conversazioneRepository;

	@Autowired
	private MessaggioService messaggioService;

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	public List<Conversazione> findAllByFkUtente(Long idUtente) throws BusinessException {
		try {
			List<Conversazione> conversazioneList = new ArrayList<>(
					conversazioneRepository.findAllByFkUtente1(idUtente));
			conversazioneList.addAll(conversazioneRepository.findAllByFkUtente2(idUtente));
			return conversazioneList;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Conversazione findNameById(Long idConversazione, Long idUtente) throws BusinessException {
		try {
			return conversazioneRepository.findNameById(idConversazione, idUtente);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void createMessaggio(Long idMittente, Long idConversazione, String contenuto) throws BusinessException {
		try {
			Messaggio messaggio = new Messaggio();
			messaggio.setId(new Random().nextLong()); //MIO DIO CHE SCHIFO
			messaggio.setIdMittente(idMittente);
			messaggio.setIdConversazione(idConversazione);
			messaggio.setContenuto(contenuto);
			messaggioService.insert(messaggio);			
			
		} catch (Exception e) {
			log.error("createMessaggio", e);
			throw new BusinessException("createMessaggio", e);
		}
	}
	
	@Override
	public void updateConversazione(Long fkUtente1, Long fkUtente2) throws BusinessException {
		try {
			Date dNow = new Date();
	    	Conversazione conversazione = (Conversazione) conversazioneRepository.findAll();
	    	if(conversazione.getFkUtente1().equals(fkUtente1)&&conversazione.getFkUtente2().equals(fkUtente2)){
	    		conversazione.setData(dNow);
		    	conversazione.setFkUtente1(fkUtente1);
		    	conversazione.setFkUtente2(fkUtente2);
		    	conversazioneRepository.save(conversazione);
	    	}else if(conversazione.getFkUtente1().equals(fkUtente2)&&conversazione.getFkUtente2().equals(fkUtente1)) {
	    		conversazione.setData(dNow);
		    	conversazione.setFkUtente1(fkUtente2);
		    	conversazione.setFkUtente2(fkUtente1);
		    	conversazioneRepository.save(conversazione);
	    	}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void nuovoMetodo() throws BusinessException {
		Messaggio messaggio = new Messaggio();
		messaggio.setId((long) 1);
		messaggio.setIdMittente((long) 1);
		messaggio.setIdConversazione((long) 1);
		messaggio.setContenuto("contenuto di prova");
		messaggioRepository.save(messaggio);
		// System.out.println(messaggioRepository.findAll().toString());
	}

	@Override
	public void nuovoMetodoPerConversazione() throws BusinessException {
		Date dNow = new Date();
		Conversazione conversazione = new Conversazione();
		conversazione.setId((long) 2);
		conversazione.setFkUtente1((long) 1);
		conversazione.setFkUtente2((long) 3);
		conversazione.setNomeUtente1("Admin");
		conversazione.setNomeUtente2("Alessandro");
		conversazione.setData(dNow);
		conversazioneRepository.save(conversazione);
	}



	/*
	 * @Override public List<Messaggio> findAllByIdConversazione(Long
	 * idConversazione) throws BusinessException { try { List<Messaggio> messaggi =
	 * new
	 * ArrayList<>(conversazioneRepository.findAllByIdConversazione(idConversazione)
	 * ); messaggi.addAll(messaggi); return messaggi; } catch (Exception e) { throw
	 * new BusinessException(e); } }
	 */
}
