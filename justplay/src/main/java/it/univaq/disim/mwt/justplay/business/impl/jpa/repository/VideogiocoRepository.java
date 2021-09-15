package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Repository
public interface VideogiocoRepository extends JpaRepository<Videogioco, Long> {

	// boolean existsByUsername(String username);

	// boolean existsByEmail(String email);

	List<Videogioco> findBy(Pageable pageable);

	List<Videogioco> findAllByPs4UrlNotNull(Pageable pageable);

	List<Videogioco> findAllByXboxUrlNotNull(Pageable pageable);

	List<Videogioco> findAllByPcUrlNotNull(Pageable pageable);

	int countByPs4UrlNotNull();

	int countByXboxUrlNotNull();

	int countByPcUrlNotNull();

}
