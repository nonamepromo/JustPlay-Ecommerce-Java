package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Repository
public interface VideogiocoInVenditaRepository extends JpaRepository<VideogiocoInVendita, Long> {

	void deleteById(Long videogiocoInVendita);

}
