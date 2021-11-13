package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.CommentoService;
import it.univaq.disim.mwt.justplay.domain.Commento;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Service
@Transactional
public class CommentoServiceImpl implements CommentoService{

	@Override
	public Commento findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCommento(Videogioco videogioco, Utente utente, String contenuto) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCommento(Commento commento) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCommento(Commento commento) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

}
