package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import javax.validation.Valid;

import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface VideogiocoService {

	List<Videogioco> findAllVideogiochi() throws BusinessException;

	void createVideogioco(Videogioco videogioco) throws BusinessException;

	ResponseGrid<Videogioco> findAllVideogiochiPaginated(RequestGrid requestGrid) throws BusinessException;

	Videogioco findVideogiocoByID(Long id) throws BusinessException;

	void updateVideogioco(Videogioco videogioco) throws BusinessException;

	void deleteVideogioco(Videogioco videogioco) throws BusinessException;
		
}
