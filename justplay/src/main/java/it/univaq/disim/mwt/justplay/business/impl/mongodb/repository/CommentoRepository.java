package it.univaq.disim.mwt.justplay.business.impl.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.univaq.disim.mwt.justplay.domain.Commento;

@Repository
public interface CommentoRepository extends MongoRepository<Commento, String>{
}
