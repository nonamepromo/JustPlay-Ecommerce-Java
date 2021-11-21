package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.MessaggioService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.UtenteRepository;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.ConversazioneRepository;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Service
@Transactional
public class ConversazioneServiceImpl implements ConversazioneService {

	@Autowired
	private ConversazioneRepository conversazioneRepository;

	@Override
	public Conversazione findById(Long idConversazione) throws BusinessException {
		return conversazioneRepository.findById(idConversazione.toString()).get();
	}

	@Override
	public Conversazione findConversazioneByUsernames(Set<String> partecipanti) throws BusinessException {
		return conversazioneRepository.findByPartecipanti(partecipanti);
	}

	@Override
	public List<Conversazione> findAllByUsername(String username) throws BusinessException {
		List<Conversazione> conversazioni = conversazioneRepository.findByPartecipanti(username);
		return conversazioni;
	}

	@Override
	public Conversazione inserisci(Conversazione conversazione) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Conversazione> findAll() throws BusinessException {
		List<Conversazione> conversazioni = conversazioneRepository.findAll();
		return conversazioni;
	}

	@Override
	public void addOrUpdateConversazione(Conversazione conversazione) throws BusinessException {
		if (conversazione.getIdConversazione() == null) {
			conversazione = conversazioneRepository.save(conversazione);
		} else {
			Conversazione conversazioneToUpdate = conversazioneRepository.findByIdConversazione(Integer.parseInt(conversazione.getIdConversazione())).get();
			//conversazioneRepository.save(conversazioneToUpdate);
			conversazioneToUpdate.setMessaggi(conversazione.getMessaggi());
			conversazioneRepository.save(conversazioneToUpdate);
		}
	}

	@Override
	public Optional<Conversazione> findByPartecipanti(List<String> partecipanti) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invia(Messaggio messaggio) throws BusinessException {
		// TODO Auto-generated method stub

	}

	// @Autowired
	// private ConversazioneRepository conversazioneRepository;

	// @Autowired
	// private UtenteRepository utenteRepository;

	// @Autowired
	// private MessaggioService messaggioService;

	// @Override
	// public Conversazione inserisci(Conversazione conversazione) throws
	// BusinessException {
	// try {
	// return conversazioneRepository.insert(conversazione);
	// } catch (Exception e) {
	// throw new BusinessException();
	// }
	// }

	// @Override
	// public void save(Conversazione conversazione) throws BusinessException {
	// try {
	// conversazioneRepository.save(conversazione);
	// } catch (Exception e) {
	// throw new BusinessException();
	// }
	// }

	// @Override
	// public Optional<Conversazione> findByPartecipanti(List<String> partecipanti)
	// throws BusinessException {
	// try {
	// return conversazioneRepository.findByPartecipanti(partecipanti);
	// } catch (Exception e) {
	// throw new BusinessException();
	// }
	// }

	// @Override
	// public void invia(Messaggio messaggio) throws BusinessException {
	// try {
	// String destinatario = messaggio.getDestinatario();
	// Optional<Utente> usernameDestinatario =
	// utenteRepository.findByUsername(destinatario);
	// if(usernameDestinatario.isPresent()) {
	// String mittente =
	// SecurityContextHolder.getContext().getAuthentication().getName();
	// messaggio.setMittente(mittente);
	// messaggioService.inserisci(messaggio);
	// Optional<Conversazione> conversazione =
	// conversazioneRepository.findByPartecipanti(Lists.newArrayList(mittente,
	// destinatario));
	// if(conversazione.isPresent()) {
	// conversazione.get().getMessaggi().add(messaggio);
	// conversazioneRepository.save(conversazione.get());
	// }else {
	// Conversazione nuovaConversazione = new Conversazione();
	// nuovaConversazione.setMessaggi(Sets.newHashSet(messaggio));
	// nuovaConversazione.setPartecipanti(Sets.newHashSet(mittente, destinatario));
	// conversazioneRepository.insert(nuovaConversazione);
	// }
	// }
	// }catch (Exception e) {
	// throw new BusinessException();
	// }
	// }

	// @Override
	// public List<Conversazione> findAll() throws BusinessException {
	// try {
	// return conversazioneRepository.findAll();
	// } catch (Exception e) {
	// throw new BusinessException();
	// }
	// }

}
