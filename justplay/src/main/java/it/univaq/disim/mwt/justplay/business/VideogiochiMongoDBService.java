package it.univaq.disim.mwt.justplay.business;

import java.util.List;
import it.univaq.disim.mwt.justplay.domain.Messaggio;
import it.univaq.disim.mwt.justplay.domain.VideogiochiMongoDB;

public interface VideogiochiMongoDBService {

	List<VideogiochiMongoDB> findAll() throws BusinessException;

}
