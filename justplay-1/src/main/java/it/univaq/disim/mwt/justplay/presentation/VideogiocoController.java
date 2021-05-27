package it.univaq.disim.mwt.justplay.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Controller
@RequestMapping("videogiochi")
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;
	
	@GetMapping("/list")
	public String showAll(Model model) throws BusinessException {
	    model.addAttribute("videogiochi", service.findAll());
	    return "videogiochi/list";
	}

	@GetMapping("/findallpaginated")
	public ResponseEntity<List<Videogioco>> findAllPaginated()
			throws BusinessException {
		return service.findAllVideogiochiPaginated();
	}
	
	@GetMapping("/details")
	public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Videogioco videogioco = service.findVideogiocoByID(id);
		model.addAttribute("videogioco", videogioco);
		return "videogiochi/details";
	}
	
	@GetMapping("/addGameToWishlist")
	public void addGameToWishlist(@RequestParam("idVideogioco") Long idVideogioco) throws BusinessException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		
		Long idUtente = Long.parseLong(id);
		
		service.addGameToWishlist(idVideogioco, idUtente);
	}
	
	@GetMapping("/create")
	public String createStart(Model model) {
		model.addAttribute("videogioco", new Videogioco());
		return "videogiochi/form";
	}
		
	@GetMapping("/delete")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		//Videogioco videogioco = service.findVideogiocoByID(id);
		//model.addAttribute("videogioco", videogioco);
		return "videogiochi/form";
	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute("videogioco") Videogioco videogioco) throws BusinessException {
		service.deleteVideogioco(videogioco);
		return "redirect:/videogiochi/list";
	}

	
}
