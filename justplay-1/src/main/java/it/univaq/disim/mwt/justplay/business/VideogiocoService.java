package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface VideogiocoService {

	List<Videogioco> findAllVideogiochi() throws BusinessException;

	ResponseEntity<List<Videogioco>> findAllVideogiochiPaginated() throws BusinessException;

	Videogioco findVideogiocoByID(Long id) throws BusinessException;

	void deleteVideogioco(Videogioco videogioco) throws BusinessException;

	void addVideogiocoDesiderato(Videogioco videogioco, Long idUtente) throws BusinessException;
	
	void addVideogiocoInVendita(Videogioco videogioco, Long idUtente) throws BusinessException;
}
