package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Controller
@RequestMapping("/common/profilo")
public class ProfiloController {

	@Autowired
	private UtenteService service;
	@Autowired
	private VideogiocoService gameService;


	@GetMapping
	public String modificaProfiloStart(@RequestParam(value = "index", defaultValue = "1") int index, Model model) throws BusinessException {
		model.addAttribute("videogiochi", gameService.findAll(3 * index));
		Utente utente = Utility.getUtente();
		Utente newUtente = service.findUtenteById(utente.getId());
		Long idUtente = utente.getId();
		getWishlist(model, idUtente);
		model.addAttribute("profilo", newUtente);
		return "/common/profilo";
	}

	public void getWishlist(Model model, Long idUtente) throws BusinessException {
		model.addAttribute("wishList", gameService.getWishlist(idUtente));
	}

	@PostMapping
	public String modificaProfilo(@ModelAttribute Utente nuovoProfilo, RedirectAttributes redirAttrs)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		nuovoProfilo.setId(utente.getId());
		service.updateProfilo(nuovoProfilo);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/common/profilo";
	}

}
