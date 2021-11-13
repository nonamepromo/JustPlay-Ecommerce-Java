package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.security.CustomUserDetails;

@Controller
@RequestMapping("/common/profilo")
public class ProfiloController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private VideogiocoService videogiocoService;

	@ModelAttribute
	public void getUtente(Model model) throws BusinessException {
		Utente utente = Utility.getUtente();
		Utente nuovoUtente = utenteService.findById(utente.getId()).get();
		model.addAttribute("utente", nuovoUtente);
	}

	public Utente utente() throws BusinessException {
		Utente utente = Utility.getUtente();
		return utente;
	}

	@RequestMapping
	public String modificaProfiloStart() throws BusinessException {
		System.out.println(utente().getVideogiochiInVendita());
		System.out.println(utente().getVideogiochiDesiderati());
		System.out.println(utente().getVideogiochiGiocati());
		return "/common/profilo";
	}

	@GetMapping("/removeGameFromWishlist")
	public String removeGameFromWishlist(@ModelAttribute("videogioco") Videogioco videogioco,
	@ModelAttribute("utente") Utente utente) throws BusinessException {
		utenteService.desiderato(utente, videogioco);
		return "redirect:/common/profilo?index=2";
	}

	@GetMapping("/removeGameFromPlayedlist")
	public String removeGameFromPlayedlist(@ModelAttribute("videogioco") Videogioco videogioco,
	@ModelAttribute("utente") Utente utente)
			throws BusinessException {
		utenteService.giocato(utente, videogioco);
		return "redirect:/common/profilo?index=3";
	}

	@GetMapping("/removeGameFromSellinglist")
	public String removeGameFromSellinglist(
			@ModelAttribute("videogiocoInVendita") VideogiocoInVendita videogiocoInVendita,
			@ModelAttribute("utente") Utente utente) throws BusinessException {
		videogiocoService.removeGameFromSellinglist(utente, videogiocoInVendita);
		return "redirect:/common/profilo?index=4";
	}

	@PostMapping
	public String modificaProfilo(@ModelAttribute Utente nuovoProfilo, RedirectAttributes redirAttrs)
			throws BusinessException {
		utenteService.update(nuovoProfilo);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/common/profilo?index=1";
	}

}
