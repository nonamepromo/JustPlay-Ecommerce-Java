package it.univaq.disim.mwt.justplay.business.impl.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.domain.Amazon;

@Repository
public interface AmazonRepository extends MongoRepository<Amazon, String> {

	List<Amazon> findAllByTitolo(String titolo) throws BusinessException;

}
