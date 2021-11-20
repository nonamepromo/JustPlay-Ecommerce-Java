package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.GameStop;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Repository
public interface GamestopRepository extends JpaRepository<GameStop, Long> {

	List<GameStop> findAllByVideogioco(Videogioco videogioco);

}
