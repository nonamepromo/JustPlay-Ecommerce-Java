package it.univaq.disim.mwt.justplay.business.impl.jpa;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.PincodeService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.PincodesRepository;
import it.univaq.disim.mwt.justplay.domain.Pincodes;

@Service
@Transactional
public class PincodeServiceImpl implements PincodeService {

	@Autowired
	private PincodesRepository pincodesRepository;

	@Override
	public List<Pincodes> findAll() throws BusinessException {
		return pincodesRepository.findAll();
	}



}
