package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;

@Repository
public interface VideogiocoDesideratoRepository extends JpaRepository<VideogiocoDesiderato, Long> {

	@Query("SELECT fkVideogioco FROM VideogiocoDesiderato vv where fk_utente = :idUtente")
	List<Long> findFksVideogiocoByFkUtente(@Param("idUtente") Long idUtente);

	void deleteByFkVideogiocoAndFkUtente(Long fkVideogioco, Long fkUtente);

}
