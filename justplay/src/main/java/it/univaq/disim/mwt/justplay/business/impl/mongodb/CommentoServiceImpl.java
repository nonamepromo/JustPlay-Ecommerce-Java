package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.CommentoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.VideogiocoRepository;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.CommentoRepository;
import it.univaq.disim.mwt.justplay.domain.Commento;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Service
@Transactional
public class CommentoServiceImpl implements CommentoService{

	@Autowired
	private CommentoRepository commentoRepository;
	
	@Override
	public Commento findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCommento(Commento commento) throws BusinessException {
		commentoRepository.save(commento);
	}

	@Override
	public void updateCommento(Commento commento) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCommento(Long idCommento) throws BusinessException {
		commentoRepository.deleteById(idCommento);;
	}

}
