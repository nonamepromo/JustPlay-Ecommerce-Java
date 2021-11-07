package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.AmazonService;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.AmazonRepository;
import it.univaq.disim.mwt.justplay.domain.Amazon;

@Service
@Transactional
public class AmazonServiceImpl implements AmazonService {

	@Autowired
	private AmazonRepository amazonRepository;

	@Override
	public List<Amazon> findAllByTitolo(String titolo) throws BusinessException {
		try {
			return amazonRepository.findAllByTitolo(titolo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}