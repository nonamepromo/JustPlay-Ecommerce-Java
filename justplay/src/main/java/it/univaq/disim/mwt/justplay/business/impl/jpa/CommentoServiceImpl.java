package it.univaq.disim.mwt.justplay.business.impl.jpa;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.CommentoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.CommentoRepository;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoRepository;
import it.univaq.disim.mwt.justplay.domain.Commento;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Service
@Transactional
public class CommentoServiceImpl implements CommentoService {

	@Autowired
	private CommentoRepository commentoRepository;

	@Override
	public void addCommento(Commento commento) throws BusinessException {
		try {
			commentoRepository.save(commento);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteCommento(Long idCommento) throws BusinessException {
		try {
			commentoRepository.deleteById(idCommento);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
