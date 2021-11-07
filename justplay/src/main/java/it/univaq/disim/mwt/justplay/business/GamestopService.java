package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Gamestop;

public interface GamestopService {

	List<Gamestop> findAllByTitolo(String titolo) throws BusinessException;

}
