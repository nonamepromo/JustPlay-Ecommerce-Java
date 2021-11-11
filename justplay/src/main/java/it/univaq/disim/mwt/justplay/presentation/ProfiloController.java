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
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Controller
@RequestMapping("/common/profilo")
public class ProfiloController {

		@Autowired
		private UtenteService utenteService;
		
		@Autowired
		private VideogiocoService videogiocoService;
	
		@GetMapping
		public String modificaProfiloStart(Model model) throws BusinessException {
			Utente utente = Utility.getUtente();
			Utente newUtente = utenteService.findById(utente.getId()).get();
			model.addAttribute("profilo", newUtente);
			model.addAttribute("wishList", utente.getVideogiochiDesiderati());
			model.addAttribute("playedList", utente.getVideogiochiGiocati());
			model.addAttribute("sellingList", utente.getVideogiochiInVendita());
			return "/common/profilo";
		}
		
		@PostMapping("/removeGameFromWishlist")
		public String removeGameFromWishlist(@RequestParam("wished")Videogioco videogioco)
				throws BusinessException {
			Utente utente = Utility.getUtente();
			utenteService.desiderato(utente, videogioco);
			return "/common/profilo";
		}
		
		@PostMapping("/removeGameFromPlayedlist")
		public String removeGameFromPlayedlist(@RequestParam("played")Videogioco videogioco)
				throws BusinessException {
			Utente utente = Utility.getUtente();
			utenteService.giocato(utente, videogioco);
			return "/common/profilo";
		}
		
		@PostMapping("/removeGameFromSellinglist")
		public String removeGameFromSellinglist(@RequestParam("selled") VideogiocoInVendita videogiocoInVendita)
				throws BusinessException {
			videogiocoService.removeGameFromSellinglist(videogiocoInVendita.getVideogioco(), videogiocoInVendita.getUtente(), videogiocoInVendita);
			return "/common/profilo";
		}
		
		@PostMapping
		public String modificaProfilo(@ModelAttribute Utente nuovoProfilo, RedirectAttributes redirAttrs)
				throws BusinessException {
			Utente utente = Utility.getUtente();
			utenteService.update(nuovoProfilo, utente.getId());
			redirAttrs.addFlashAttribute("success", "");
			return "redirect:/common/profilo?index=1";
		}
	
}
