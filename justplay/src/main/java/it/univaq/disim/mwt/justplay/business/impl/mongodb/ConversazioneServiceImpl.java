package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.ConversazioneRepository;
import it.univaq.disim.mwt.justplay.domain.Conversazione;

@Service
@Transactional
public class ConversazioneServiceImpl implements ConversazioneService {

	@Autowired
	private ConversazioneRepository conversazioneRepository;

	@Override
	public Conversazione findConversazioneByUsernames(Set<String> partecipanti) throws BusinessException {
		try {
			return conversazioneRepository.findByPartecipanti(partecipanti);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Conversazione> findAllByUsername(String username) throws BusinessException {
		try {
			List<Conversazione> conversazioni = conversazioneRepository.findByPartecipanti(username);
			return conversazioni;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void addOrUpdateConversazione(Conversazione conversazione) throws BusinessException {
		try {
			if (conversazione.getId() == null) {
				conversazione.setId(UUID.randomUUID().toString());
				conversazione = conversazioneRepository.save(conversazione);
			} else {
				Conversazione conversazioneToUpdate = conversazioneRepository.findById(conversazione.getId()).get();
				conversazioneToUpdate.setMessaggi(conversazione.getMessaggi());
				conversazioneRepository.save(conversazioneToUpdate);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
