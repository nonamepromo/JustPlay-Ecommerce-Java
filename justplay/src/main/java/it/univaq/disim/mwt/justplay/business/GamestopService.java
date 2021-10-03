package it.univaq.disim.mwt.justplay.business;

import it.univaq.disim.mwt.justplay.domain.Gamestop;

public interface GamestopService {

	Gamestop findAllByFkVideogioco(Long idVideogioco) throws BusinessException;

	void popolazione() throws BusinessException;
    
}
