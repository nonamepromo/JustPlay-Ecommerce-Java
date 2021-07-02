package it.univaq.disim.mwt.justplay.presentation;

import java.util.List;

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

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

@Controller
@RequestMapping("/common")
public class ConversazioneController {

    @Autowired
	private ConversazioneService service;

    @GetMapping("/conversations-list")
	public String showAll(Model model) throws BusinessException {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if(authentication.getPrincipal() != "anonymousUser") {
			model.addAttribute("idUtente", Utility.getUtente().getId());

		    Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
		    model.addAttribute("conversazioni", service.findAllByFkUtente(idUtente));
	    }
		return "common/conversations-list";
	    
	}

	//INTERESSANTE
	// @GetMapping("/conversation")
	// public String findConversazione(Model model, @RequestParam(value = "idConversazione") Long idConversazione) throws BusinessException {
	//     model.addAttribute("fk_conversazione", idConversazione);
	//     model.addAttribute("messaggi", service.findMessaggiByFkUtenti(idConversazione));
	//     return "common/conversations-list";
	    
	// }
	
	@GetMapping("/conversation")
	public String findConversazione(Model model, @RequestParam(value = "idConversazione") Long idConversazione) throws BusinessException {
	    Long idUtente = Utility.getUtente().getId();
		model.addAttribute("nomeUtente", service.findNameByIdConversazione(idConversazione, idUtente));
	    model.addAttribute("messaggi", service.findMessaggiByFkConversazione(idConversazione));
		Messaggio messaggio = new Messaggio();
		model.addAttribute("messaggio", messaggio);
		model.addAttribute("idUtente", idUtente);
		model.addAttribute("idConversazione", idConversazione);
		
	    return "common/conversation";
	    
	}

	@PostMapping("/createMessaggio")
	public String createMessaggio(Model model, @ModelAttribute Messaggio messaggio, @RequestParam(value = "idUtente") Long idUtente, @RequestParam(value = "idConversazione") Long idConversazione)throws BusinessException {
		
		service.createMessaggio(idUtente, idConversazione, messaggio.getContenuto());
		model.addAttribute("nomeUtente", service.findNameByIdConversazione(idConversazione, idUtente));

	    return "redirect:/common/conversation?idConversazione=" + idConversazione;
	}

    @PostMapping("/createConversazione")
	public String createConversazione(@RequestParam(value = "fk_conversazione") Long fk_conversazione, @RequestParam(value = "contenuto") String contenuto) throws BusinessException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		
		Long idUtente = Long.parseLong(id);
		
		service.createMessaggio(idUtente, fk_conversazione, contenuto);
		return "common/conversations-list";
	}
    
    @PostMapping("/updateConversazione")
	public String updateConversazione(@RequestParam(value = "fk_utente") Long idUtenteContattato, @RequestParam(value = "nuovoMessaggio") String nuovoMessaggio) throws BusinessException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		
		Long idUtente = Long.parseLong(id);
		service.updateConversazione(idUtente, idUtenteContattato);
		return "common/conversations-list";
	}

}