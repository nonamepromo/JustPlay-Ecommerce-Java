package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Controller
@RequestMapping("/common")
public class ConversazioneController {

    @Autowired
	private ConversazioneService service;

    @GetMapping("/conversations-list")
	public String showAll(Model model) throws BusinessException {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(authentication.getPrincipal() != "anonymousUser") {

		    Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
		    model.addAttribute("conversazioni", service.findAllByFkUtente(idUtente));
	    }
		return "common/conversations-list";
	    
	}
	
	@GetMapping("/conversation")
	public String findConversazione(Model model, @RequestParam(value = "fk_utente1") Long idUtente, @RequestParam(value = "fk_utente2") Long idUtenteContattato) throws BusinessException {
		Conversazione conversazione = service.findConversazioneByFkUtenti(idUtenteContattato, idUtente);
	    model.addAttribute("conversazione", conversazione);
		String[] messaggi = null;
		messaggi = conversazione.getChat().split(";");
		model.addAttribute("messaggi", messaggi);
	    return "common/conversations-list";
	    
	}

    @PostMapping("/createConversazione")
	public String createConversazione(@RequestParam(value = "fk_utente") Long idUtenteContattato, @RequestParam(value = "messaggio") String messaggio) throws BusinessException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		
		Long idUtente = Long.parseLong(id);
		
		service.createConversazione(idUtente, idUtenteContattato, messaggio);
		return "common/conversations-list";
	}
    
    @PostMapping("/updateConversazione")
	public String updateConversazione(@RequestParam(value = "fk_utente") Long idUtenteContattato, @RequestParam(value = "nuovoMessaggio") String nuovoMessaggio) throws BusinessException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		
		Long idUtente = Long.parseLong(id);
		String chat = service.findConversazioneByFkUtenti(idUtenteContattato, idUtente).getChat();
		chat = chat + idUtente.toString() + nuovoMessaggio;
		service.updateConversazione(idUtente, idUtenteContattato, chat);
		return "common/conversations-list";
	}

}