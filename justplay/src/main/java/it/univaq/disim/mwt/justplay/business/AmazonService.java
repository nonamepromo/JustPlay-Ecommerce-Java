package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Amazon;

public interface AmazonService{

    Amazon findByidVideogioco(Long idVideogioco) throws BusinessException;

}