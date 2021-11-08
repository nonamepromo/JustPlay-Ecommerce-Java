package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.GamestopService;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.GamestopRepository;
import it.univaq.disim.mwt.justplay.domain.Gamestop;

@Service
@Transactional
public class GamestopServiceImpl implements GamestopService {

	@Autowired
	private GamestopRepository gamestopRepository;

	@Override
	public List<Gamestop> findAllByTitolo(String titolo) throws BusinessException {
		try {
			return gamestopRepository.findAllByTitolo(titolo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
}