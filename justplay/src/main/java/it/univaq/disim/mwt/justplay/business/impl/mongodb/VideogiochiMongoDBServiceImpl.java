package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiochiMongoDBService;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.VideogiochiMongoRepository;
import it.univaq.disim.mwt.justplay.domain.VideogiochiMongoDB;

@Service
@Transactional
public class VideogiochiMongoDBServiceImpl implements VideogiochiMongoDBService {

	@Autowired
	private VideogiochiMongoRepository videogiochiMongoRepository;

	@Override
	public List<VideogiochiMongoDB> findAll() throws BusinessException {
		try {
			List<VideogiochiMongoDB> videogiochiMongoDB = new ArrayList<>(videogiochiMongoRepository.findAll());
			return videogiochiMongoDB;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
