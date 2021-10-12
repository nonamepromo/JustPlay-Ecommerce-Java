package it.univaq.disim.mwt.justplay.business.impl.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.domain.Amazon;

@Repository
public interface AmazonRepository extends MongoRepository<Amazon, String> {

	Amazon findAllByFkVideogioco(Long idVideogioco) throws BusinessException;

}
