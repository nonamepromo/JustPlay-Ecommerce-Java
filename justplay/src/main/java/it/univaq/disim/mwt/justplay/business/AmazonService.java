package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Amazon;

public interface AmazonService {

	Amazon findAllByFkVideogioco(Long idVideogioco) throws BusinessException;

	// MI SERVIVA SOLO PER POPOLARE DB MONGO CHE NON MI ANDAVA DI FARLO A MANO
	void popolazione() throws BusinessException;

}