package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.univaq.disim.mwt.justplay.domain.VideogiocoGiocato;

@Repository
public interface VideogiocoGiocatoRepository extends JpaRepository<VideogiocoGiocato, Long> {

	//@Query("SELECT fkVideogioco FROM VideogiocoGiocato vv where fk_utente = :idUtente")
	//List<Long> findFksVideogiocoByFkUtente(@Param("idUtente") Long idUtente);

	//void deleteByFkVideogiocoAndFkUtente(Long fkVideogioco, Long fkUtente);

	List<VideogiocoGiocato> findVideogiochiGiocatiByUtente(Utente utente);

	void deleteByVideogiocoAndUtente(Videogioco videogioco, Utente utente);

}
