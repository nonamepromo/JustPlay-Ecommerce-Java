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
		
		@ModelAttribute
		public void getUtente(Model model) throws BusinessException {
			Utente utente = Utility.getUtente();
			model.addAttribute("utente", utente);
		}
		
		public Utente utente() throws BusinessException {
			Utente utente = Utility.getUtente();
			return utente;
		}
	
		@GetMapping
		public String modificaProfiloStart(Model model) throws BusinessException {
			return "/common/profilo";
		}
		
		@GetMapping("/removeGameFromWishlist")
		public String removeGameFromWishlist(@RequestParam("wished")Long idVideogioco)
				throws BusinessException {
			utenteService.desiderato(utente(), videogiocoService.findById(idVideogioco));
			return "redirect:/common/profilo?index=2";
		}
		
		@GetMapping("/removeGameFromPlayedlist")
		public String removeGameFromPlayedlist(@RequestParam("played")Long idVideogioco)
				throws BusinessException {
			utenteService.giocato(utente(), videogiocoService.findById(idVideogioco));
			return "redirect:/common/profilo?index=3";
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
			utenteService.update(nuovoProfilo, Utility.getUtente().getId());
			redirAttrs.addFlashAttribute("success", "");
			return "redirect:/common/profilo?index=1";
		}
	
}
