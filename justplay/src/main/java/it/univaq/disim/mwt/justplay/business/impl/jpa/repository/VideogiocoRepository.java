package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface VideogiocoRepository {

	List<Videogioco> findAllByPs4True(Pageable pageable);

	List<Videogioco> findAllByXboxTrue(Pageable pageable);

	List<Videogioco> findAllByPcTrue(Pageable pageable);

	List<Videogioco> findByTitoloContaining(String searchString, Pageable page);
	
}
