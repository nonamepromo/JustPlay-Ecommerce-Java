package it.univaq.disim.mwt.justplay.business;

import java.util.List;

import it.univaq.disim.mwt.justplay.domain.Pincodes;

public interface PincodeService {

	List<Pincodes> findAll() throws BusinessException;
}