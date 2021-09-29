package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Repository
public interface VideogiocoDesideratoRepository extends JpaRepository<VideogiocoDesiderato, Long> {

	@Query("SELECT fkVideogioco FROM VideogiocoDesiderato vv where fk_utente = :idUtente")
    List<Long> findFksVideogiocoByFkUtente(@Param("idUtente") Long idUtente);

	void deleteByFkVideogiocoAndFkUtente(Long fkVideogioco, Long fkUtente);
	
	// boolean existsByUsername(String username);

	// boolean existsByEmail(String email);

}