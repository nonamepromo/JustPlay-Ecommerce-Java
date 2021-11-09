package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Utente findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
