package it.univaq.disim.mwt.justplay.presentation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.boot.model.convert.spi.ConverterDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.MessaggioService;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.UtenteRepository;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Messaggio;
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

	/*
	 * Aggiunge al model la lista di tutte le conversazioni appartenenti all'utente
	 * connesso
	 */
	@GetMapping("/conversations-list")
	public String showAll(@ModelAttribute("utente") Utente utente, Model model, Authentication authentication)
			throws BusinessException {
		List<Conversazione> conversazioni = conversazioneService.findAllByUsername(utente.getUsername());
		model.addAttribute("conversazioni", conversazioni);
		return "common/conversations-list";
	}

	@GetMapping("/conversation")
	public String conversation(@ModelAttribute("utente") Utente utente,
			@RequestParam("usernamePartecipante") String usernamePartecipante, Model model) throws BusinessException {
		Set<String> partecipanti = new HashSet<String>();
		partecipanti.add(usernamePartecipante);
		partecipanti.add(utente.getUsername());

		Conversazione conversazione = conversazioneService.findConversazioneByUsernames(partecipanti);
		model.addAttribute("conversazione", conversazione);

		return "common/conversation";
	}

	@PostMapping("/createMessaggio")
	public String createMessaggio(Model model, @ModelAttribute("utente") Utente utente,
			@ModelAttribute("conversazione") Conversazione conversazione,
			@ModelAttribute("messaggio") Messaggio messaggio,
			@RequestParam("usernamePartecipante") String usernamePartecipante) throws BusinessException {

		messaggio.setMittente(utente.getUsername());
		messaggio.setDestinatario(usernamePartecipante);
		messaggio.setId(UUID.randomUUID().toString());
		
		conversazione.getMessaggi().add(messaggio);
		
		conversazioneService.addOrUpdateConversazione(conversazione);

		return "redirect:/common/conversation?usernamePartecipante=" + usernamePartecipante;
	}

}
