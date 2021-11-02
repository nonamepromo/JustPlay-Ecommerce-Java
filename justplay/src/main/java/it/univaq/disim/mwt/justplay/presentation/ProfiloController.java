package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Controller
@RequestMapping("/common/profilo")
public class ProfiloController {

	@Autowired
	private UtenteService service;
	@Autowired
	private VideogiocoService gameService;
	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public String modificaProfiloStart(Model model) throws BusinessException {
		model.addAttribute("videogiochi", gameService.findAllProfile());
		Utente utente = Utility.getUtente();
		Utente newUtente = service.findById(utente.getId()).get();
		Long idUtente = utente.getId();
		getWishlist(model, idUtente);
		getPlayedlist(model, idUtente);
		getSellinglist(model, idUtente);
		model.addAttribute("profilo", newUtente);
		return "/common/profilo";
	}

	public void getWishlist(Model model, Long idUtente) throws BusinessException {
		model.addAttribute("wishList", gameService.getWishlist(idUtente));
	}

	public void getPlayedlist(Model model, Long idUtente) throws BusinessException {
		model.addAttribute("playedList", gameService.getPlayedlist(utenteService.findById(idUtente).get()));
	}

	public void getSellinglist(Model model, Long idUtente) throws BusinessException {
		model.addAttribute("sellingList", gameService.getUtenteSellinglist(idUtente));
	}

	@PostMapping("/removeGameFromWishlist")
	public String removeGameFromWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		Long idUtente = utente.getId();
		gameService.removeGameFromWishlist(idVideogioco, idUtente);
		return "/common/profilo";
	}

	@PostMapping("/removeGameFromPlayedlist")
	public String removeGameFromPlayedlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		Long idUtente = utente.getId();
		//gameService.removeGameFromPlayedlist(idVideogioco, idUtente);
		Videogioco videogioco = gameService.findVideogiocoByID(idVideogioco);
		gameService.removeGameFromPlayedlist(videogioco, utente);
		return "/common/profilo";
	}

	@PostMapping
	public String modificaProfilo(@ModelAttribute Utente nuovoProfilo, RedirectAttributes redirAttrs)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		service.save(nuovoProfilo, utente.getId());
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/common/profilo?index=1";
	}

}
