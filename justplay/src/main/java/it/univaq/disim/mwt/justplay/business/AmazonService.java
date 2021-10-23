package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Amazon;

public interface AmazonService {

	Amazon findAllByFkVideogioco(Long idVideogioco) throws BusinessException;

	void mongoAmazon() throws BusinessException;

}