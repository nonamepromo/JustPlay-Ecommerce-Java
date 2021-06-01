package it.univaq.disim.mwt.justplay.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;

@Controller
@RequestMapping("videogiochi")
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;
	
	@GetMapping("/list")
	public String showAll(Model model) throws BusinessException {
	    model.addAttribute("videogiochi", service.findAll());
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
	    
		getWishlist(model, idUtente);
		
	    return "videogiochi/list";
	}
	
	public void getWishlist(Model model, Long idUtente) throws BusinessException {

	    model.addAttribute("wishList", service.getWishlist(idUtente));
	}
	
//	@GetMapping("/list")
//	public String getWishList(Model model) throws BusinessException {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String id = authentication.getPrincipal().toString();
//		
//		Long idUtente = Long.parseLong(id);
//	    model.addAttribute("wishList", service.getWishlist(idUtente));
//	    return "videogiochi/list";
//	}

//	@GetMapping("/findallpaginated")
//	public ResponseEntity<List<Videogioco>> findAllPaginated()
//			throws BusinessException {
//		return service.findAllVideogiochiPaginated();
//	}
	
	@GetMapping("/details")
	public String details(@RequestParam("id") Long id, Model model) throws BusinessException {
		Videogioco videogioco = service.findVideogiocoByID(id);
		model.addAttribute("videogioco", videogioco);
		return "videogiochi/details";
	}
	
	@PostMapping("/addGameToWishlist")
	public String addGameToWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco) throws BusinessException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		
		Long idUtente = Long.parseLong(id);
		
		service.addGameToWishlist(idVideogioco, idUtente);
	    return "videogiochi/list";
	}
	
	@PostMapping("/removeGameFromWishlist")
	public String removeGameFromWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco) throws BusinessException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		
		Long idUtente = Long.parseLong(id);
		
		service.removeGameFromWishlist(idVideogioco, idUtente);
	    return "videogiochi/list";
	}
	
	// @GetMapping("/checkIfGameIsDesidered")
	// public ResponseEntity<String> checkIfGameIsDesidered(@RequestParam("idUtente") Long idUtente, @RequestParam("idVideogioco") Long idVideogioco) throws BusinessException {
	// 	boolean exist = service.checkIfGameIsDesidered(idUtente, idVideogioco);
	// 	String result = null;
	// 	if(exist) {
	// 		result = "SUCCESS";
	// 		}
	// 	else {
	// 		result = "FAIL";
	// 	}
	// 	return ResponseEntity.ok(result);

	// }
	
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
