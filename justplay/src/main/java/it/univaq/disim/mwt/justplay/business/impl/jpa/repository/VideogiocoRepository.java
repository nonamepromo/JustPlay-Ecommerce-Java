package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

@Repository
public interface VideogiocoRepository extends JpaRepository<Videogioco, Long> {
	
	
	Videogioco findByLikesAndId(Long idUtente, Long fkVideogioco);

	

	List<Videogioco> findBy(Pageable pageable);

	List<Videogioco> findAllByPs4UrlNotNull(Pageable pageable);

	List<Videogioco> findAllByXboxUrlNotNull(Pageable pageable);

	List<Videogioco> findAllByPcUrlNotNull(Pageable pageable);

	@Query("SELECT v FROM Videogioco v WHERE v.titolo LIKE %:searchString%")
	List<Videogioco> findResearchedBy(@Param("searchString") String searchString, Pageable pageable);

	List<Videogioco> findByTitoloContaining(String searchString);

	List<Videogioco> findByTitoloContaining(String searchString, Pageable page);

	int countByTitoloContaining(String searchString);

	int countByPs4UrlNotNull();

	int countByXboxUrlNotNull();

	int countByPcUrlNotNull();

}
