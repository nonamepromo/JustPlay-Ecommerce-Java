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
		amazon.setFkVideogioco((long) 1);
		amazon.setPrezzoAmazon(22.99);
		amazon.setAmazonUrl("https://www.amazon.it/Witcher-III-Game-Year-PlayStation/dp/B01KJZYXW6/ref=asc_df_B01KJZYXW6/?tag=googshopit-21&linkCode=df0&hvadid=103300905779&hvpos=&hvnetw=g&hvrand=1587090134071365637&hvpone=&hvptwo=&hvqmt=&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=1008623&hvtargid=pla-273484758145&psc=1");
		amazonRepository.save(amazon);
		System.out.println(amazonRepository.findAll().toString());
	}

}