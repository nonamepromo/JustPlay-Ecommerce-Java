package it.univaq.disim.mwt.justplay.presentation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.MessaggioService;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.UtenteRepository;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Controller
@RequestMapping("/common")
public class ConversazioneController {
	
	@Autowired
	private ConversazioneService conversazioneService;
	
	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private MessaggioService messaggioService;
	
	@ModelAttribute
	public void getUtente(Model model) throws BusinessException {
		Utente utente = Utility.getUtente();
		model.addAttribute("utente", utente);
	}
	
	public Utente utente() throws BusinessException {
		Utente utente = Utility.getUtente();
		return utente;
	}
	
	/*
	 * Aggiunge al model la lista di tutte le conversazioni appartenenti all'utente
	 * connesso
	 */
	@GetMapping("/conversations-list")
	public String showAll(Model model, Authentication authentication) throws BusinessException {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			List<Utente> utenti = utenteRepository.findAll();
			System.out.println(utenti);
			utenti.forEach(utente ->{
				
			});
			
			model.addAttribute("conversazioni", conversazioneService.findAll());
		}
		return "common/conversations-list";

	}


}
