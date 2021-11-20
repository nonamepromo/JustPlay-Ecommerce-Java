package it.univaq.disim.mwt.justplay.business.impl.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Amazon;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Repository
public interface AmazonRepository extends JpaRepository<Amazon, Long> {

	List<Amazon> findAllByVideogioco(Videogioco videogioco);

}
