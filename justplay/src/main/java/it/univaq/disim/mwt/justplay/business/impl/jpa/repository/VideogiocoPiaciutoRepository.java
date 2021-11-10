package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

@Repository
public interface VideogiocoPiaciutoRepository extends JpaRepository<VideogiocoPiaciuto, Long> {

	VideogiocoPiaciuto findByUtenteAndVideogioco(Utente utente, Videogioco videogioco);

}
