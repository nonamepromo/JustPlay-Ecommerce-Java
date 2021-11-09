package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Repository
public interface VideogiocoInVenditaRepository extends JpaRepository<VideogiocoInVendita, Long> {

	List<VideogiocoInVendita> findByVideogioco(Videogioco videogioco);

	List<VideogiocoInVendita> findByUtente(Utente utente);

	void deleteByVideogiocoAndUtenteAndVideogiocoInVendita(Videogioco videogioco, Utente utente, VideogiocoInVendita videogiocoInVendita);

	List<VideogiocoInVendita> findAllByVideogioco(Videogioco videogioco);

	List<VideogiocoInVendita> findAllByVideogiocoAndUtente(Videogioco videogioco, Utente utente);

}
