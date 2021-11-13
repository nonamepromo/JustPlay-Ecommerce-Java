package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.univaq.disim.mwt.justplay.domain.Videogioco;

public interface VideogiocoRepository extends JpaRepository<Videogioco, Long>  {
	
	Page<Videogioco> findByTitoloContaining(Pageable page, String searchString);

	Page<Videogioco> findByTitoloContainingAndPs4(Pageable pageable, String searchString, boolean ps4);
	
	Page<Videogioco> findByTitoloContainingAndXbox(Pageable pageable, String searchString, boolean xbox);
	
	Page<Videogioco> findByTitoloContainingAndPc(Pageable pageable, String searchString, boolean pc);

	// List<Videogioco> findAllByPs4True(Pageable pageable);

	// List<Videogioco> findAllByXboxTrue(Pageable pageable);

	// List<Videogioco> findAllByPcTrue(Pageable pageable);

		
	// long count();

}
