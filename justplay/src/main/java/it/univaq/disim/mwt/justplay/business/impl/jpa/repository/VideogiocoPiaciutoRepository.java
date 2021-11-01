package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

@Repository
public interface VideogiocoPiaciutoRepository extends JpaRepository<VideogiocoPiaciuto, Long> {

	//VideogiocoPiaciuto findByFkUtenteAndFkVideogioco(Long idUtente, Long fkVideogioco);
	
	//void deleteByFkVideogiocoAndFkUtente(Long fkVideogioco, Long fkUtente);
	
	//int countByFkVideogiocoAndPiaciuto(Long fkVideogioco, boolean piaciuto);
	
	
	
	
	
	void deleteByUtenteAndVideogioco(Utente utente, Videogioco videogioco);
	
	int countByVideogiocoAndPiaciuto(Videogioco videogioco, boolean piaciuto);
	
	VideogiocoPiaciuto findByUtenteAndVideogioco(Utente utente, Videogioco videogioco);
}
