package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface VideogiocoRepository extends JpaRepository<Videogioco, Long>  {
	
	Page<Videogioco> findByTitoloContaining(Pageable page, String searchString);

	Page<Videogioco> findByTitoloContainingAndPs4(Pageable pageable, String searchString, boolean ps4);
	
	Page<Videogioco> findByTitoloContainingAndXbox(Pageable pageable, String searchString, boolean xbox);
	
	Page<Videogioco> findByTitoloContainingAndPc(Pageable pageable, String searchString, boolean pc);

}
