package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Messaggio;

public interface MessaggioService {

	void insert(Messaggio messaggio) throws BusinessException;

}