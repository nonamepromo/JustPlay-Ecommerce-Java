package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.ArrayList;
import java.util.Date;
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
import it.univaq.disim.mwt.justplay.domain.Utente;
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
			List<Conversazione> conversazioneList = new ArrayList<>(conversazioneRepository.findAllByFkUtente1(idUtente));
			conversazioneList.addAll(conversazioneRepository.findAllByFkUtente2(idUtente));
			return conversazioneList;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Messaggio> findMessaggiById(Long idConversazione) throws BusinessException {
		try {
			return conversazioneRepository.findMessaggiById(idConversazione);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public String findNameById(Long idConversazione, Long idUtente) throws BusinessException {
		try {
			return conversazioneRepository.findNameById(idConversazione, idUtente);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void createMessaggio(Long fkMittente, Long idConversazione, String contenuto) throws BusinessException {
		try {
			Messaggio messaggio = new Messaggio();
			messaggio.setFk_mittente(fkMittente);
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
		// TODO Auto-generated method stub
	}
	
	@Override
	public void nuovoMetodo() throws BusinessException{
		Messaggio messaggio = new Messaggio();
		messaggio.setId((long) 1);
		messaggio.setFk_mittente((long) 1);
		messaggio.setIdConversazione((long) 1);
		messaggio.setContenuto("contenuto");
		messaggioRepository.save(messaggio);
		//System.out.println(messaggioRepository.findAll().toString());
	}

	@Override
	public void nuovoMetodoPerConversazione() throws BusinessException {
		Date dNow = new Date();
		Conversazione conversazione = new Conversazione();
		conversazione.setId((long)1);
		conversazione.setFkUtente1((long) 1);
		conversazione.setFkUtente2((long) 2);
		conversazione.setNomeUtente1("Admin");
		conversazione.setNomeUtente2("Muccini");
		conversazione.setData(dNow);
		conversazioneRepository.save(conversazione);
	}
	
	

}
