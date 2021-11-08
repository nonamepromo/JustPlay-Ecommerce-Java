package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;

@Controller
public class IndexController {

	@Autowired
	private VideogiocoService videogiocoService;
	
	@GetMapping("/")
	public String index() throws BusinessException{
		videogiocoService.addGameFromMdb();
		return "index";
	}
	
}
