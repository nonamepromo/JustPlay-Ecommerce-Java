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
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Repository
public interface VideogiocoInVenditaRepository extends JpaRepository<VideogiocoInVendita, Long> {

	@Query("SELECT id FROM VideogiocoInVendita vv where fk_videogioco = :idVideogioco")
    List<Long> findIdsByFkVideogioco(@Param("idVideogioco") Long idVideogioco);

	// boolean existsByUsername(String username);

	// boolean existsByEmail(String email);

}
