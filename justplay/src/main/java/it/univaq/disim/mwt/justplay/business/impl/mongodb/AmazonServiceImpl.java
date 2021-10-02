package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.AmazonService;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.AmazonRepository;
import it.univaq.disim.mwt.justplay.domain.Amazon;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AmazonServiceImpl implements AmazonService {

	@Autowired
	private AmazonRepository amazonRepository;

	@Override
	public Amazon findAllByFkVideogioco(Long idVideogioco) throws BusinessException {
		try {
			return amazonRepository.findAllByFkVideogioco(idVideogioco);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	//MI SERVIVA SOLO PER POPOLARE DB MONGO CHE NON MI ANDAVA DI FARLO A MANO
	@Override
	public void popolazione() throws BusinessException {
		Amazon amazon = new Amazon();
		amazon.setId((long) 2);
		amazon.setFkVideogioco((long) 1);
		amazon.setPrezzoAmazon(22.99);
		amazon.setAmazonUrl("https://www.amazon.it/Witcher-III-Game-Year-PlayStation/dp/B01KJZYXW6/ref=sr_1_4?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&dchild=1&keywords=the+witcher&qid=1633204503&qsid=262-1433747-3953066&sr=8-4&sres=8842932418%2C8842932426%2C8842932434%2CB01KJZYXW6%2C8842932795%2C8842932779%2C8842932787%2C8842932752%2C8842932760%2C8891246816%2C1473232279%2CB08PTZQGHN%2C8891249106%2C0316029181%2CB07WDZ6LD5%2CB07WFLGNQX%2CB07JZFYR4Y%2C031670329X%2CB07WH4GGKL%2C1506713947&srpt=ABIS_BOOK");
		amazonRepository.save(amazon);
		System.out.println(amazonRepository.findAll().toString());
	}

}