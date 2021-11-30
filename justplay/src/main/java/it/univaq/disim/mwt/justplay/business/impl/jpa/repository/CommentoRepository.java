package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Commento;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, String> {
	
    void deleteById(Long idCommento);
    
}
