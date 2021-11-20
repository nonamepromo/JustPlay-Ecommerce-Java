package it.univaq.disim.mwt.justplay.presentation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.CommentoService;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Commento;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Controller
@RequestMapping("/details")
public class DetailsController {

	// @Autowired
	// private VideogiocoService service;
	
	// @Autowired
	// private CommentoService commentoService;

	// @ModelAttribute
	// public void getUtente(Model model) throws BusinessException {
	// 	if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
	// 		Utente utente = Utility.getUtente();
	// 		model.addAttribute("utente", utente);
	// 	}
	// }
    
	// @ModelAttribute
	// public void getVideogioco(@RequestParam("idVideogioco") Long idVideogioco, Model model) throws BusinessException {
	// 	Videogioco videogioco = service.findById(idVideogioco);
	// 	model.addAttribute("videogioco", videogioco);
	// }	

	// @GetMapping("")
	// public String details(@RequestParam("idVideogioco") Long idVideogioco, Model model) throws BusinessException {
	// 	return "/details";
	// }	

	// @PostMapping("/addCommento")
	// public String details(@ModelAttribute("utente") Utente utente, @ModelAttribute("videogioco") Videogioco videogioco, @ModelAttribute("commento") Commento commento, Model model) throws BusinessException {
	// 	commento.setUtente(utente);
	// 	commento.setVideogioco(videogioco);
	// 	commento.setId((long)20);
	// 	commentoService.addCommento(commento);
	// 	return "redirect:/details?idVideogioco=" + videogioco.getId();
	// }

}
