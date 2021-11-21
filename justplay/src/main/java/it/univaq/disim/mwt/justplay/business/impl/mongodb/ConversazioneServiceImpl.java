package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
	public Conversazione findConversazioneByUsernames(Set<String> partecipanti) throws BusinessException {
		return conversazioneRepository.findByPartecipanti(partecipanti);
	}

	@Override
	public List<Conversazione> findAllByUsername(String username) throws BusinessException {
		List<Conversazione> conversazioni = conversazioneRepository.findByPartecipanti(username);
		return conversazioni;
	}

	@Override
	public void addOrUpdateConversazione(Conversazione conversazione) throws BusinessException {
		if (conversazione.getId() == null) {
			conversazione.setId(UUID.randomUUID().toString());
			conversazione = conversazioneRepository.save(conversazione);
		} else {
			Conversazione conversazioneToUpdate = conversazioneRepository.findById(conversazione.getId()).get();
			conversazioneToUpdate.setMessaggi(conversazione.getMessaggi());
			conversazioneRepository.save(conversazioneToUpdate);
		}
	}


}
