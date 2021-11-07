package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Amazon;

public interface AmazonService {

	List<Amazon> findAllByTitolo(String titolo) throws BusinessException;

}