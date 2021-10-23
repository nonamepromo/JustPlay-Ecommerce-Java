package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.GamestopService;
import it.univaq.disim.mwt.justplay.business.impl.mongodb.repository.GamestopRepository;
import it.univaq.disim.mwt.justplay.domain.Gamestop;

@Service
@Transactional
public class GamestopServiceImpl implements GamestopService {

	@Autowired
	private GamestopRepository gamestopRepository;

	@Override
	public Gamestop findAllByFkVideogioco(Long idVideogioco) throws BusinessException {
		try {
			return gamestopRepository.findAllByFkVideogioco(idVideogioco);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void mongoGamestop() throws BusinessException {
		Gamestop gamestop = new Gamestop();
		gamestop.setFkVideogioco((long) 1);
		gamestop.setPrezzo(22.99);
		gamestop.setUrl(
				"https://www.gamestop.it/XboxONE/Games/113081/gears-5?group=layout&utm_source=3169789&utm_medium=banner&utm_campaign=TradeDoubler");
		gamestopRepository.save(gamestop);
		System.out.println(gamestopRepository.findAll().toString());
	}

}