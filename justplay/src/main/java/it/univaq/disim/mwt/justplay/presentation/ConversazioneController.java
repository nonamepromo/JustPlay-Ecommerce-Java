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

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.MessaggioService;
import it.univaq.disim.mwt.justplay.domain.Messaggio;

@Controller
@RequestMapping("/common")
public class ConversazioneController {

	@Autowired
	private ConversazioneService conversazioneService;

	@Autowired
	private MessaggioService messaggioService;

	/*
	 * Aggiunge al model la lista di tutte le conversazioni appartenenti all'utente
	 * connesso
	 */
	@GetMapping("/conversations-list")
	public String showAll(Model model) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() != "anonymousUser") {
			model.addAttribute("idUtente", Utility.getUtente().getId());
			Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
			model.addAttribute("conversazioni", conversazioneService.findAllByFkUtente(idUtente));
		}
		return "common/conversations-list";

	}

	/*
	 * Prende in argomento l'id di una conversazione e aggiunge al Model l'attributo
	 * relativo alla conversazione corrispondente
	 */
	@GetMapping("/conversation")
	public String findConversazione(Model model, @RequestParam(value = "idConversazione") Long idConversazione)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		if (idUtente == conversazioneService.findNameByIdConversazione(idConversazione, idUtente).getFkUtente1()) {
			model.addAttribute("nomeUtente",
					conversazioneService.findNameByIdConversazione(idConversazione, idUtente).getNomeUtente2());
		} else {
			model.addAttribute("nomeUtente",
					conversazioneService.findNameByIdConversazione(idConversazione, idUtente).getNomeUtente1());
		}
		model.addAttribute("messaggi", messaggioService.findAllByIdConversazione(idConversazione));
		Messaggio messaggio = new Messaggio();
		model.addAttribute("messaggio", messaggio);
		model.addAttribute("idUtente", idUtente);
		model.addAttribute("idConversazione", idConversazione);

		return "common/conversation";
	}

	/*
	 * Crea un record messaggio prendendo in argomento un oggetto Messaggio, l'id
	 * dell'utente con il quale l'utente connesso sta interagendo, l'id della
	 * conversazione corrispondente
	 */
	@PostMapping("/createMessaggio")
	public String createMessaggio(Model model, @ModelAttribute Messaggio messaggio,
			@RequestParam(value = "idUtente") Long idUtente,
			@RequestParam(value = "idConversazione") Long idConversazione) throws BusinessException {
		conversazioneService.createMessaggio(idUtente, idConversazione, messaggio.getContenuto());
		model.addAttribute("nomeUtente",
				conversazioneService.findNameByIdConversazione(idConversazione, idUtente).getNomeUtente1());
		return "redirect:/common/conversation?idConversazione=" + idConversazione + "&nomeUtente="
				+ conversazioneService.findNameByIdConversazione(idConversazione, idUtente).getNomeUtente2();
	}

	@PostMapping("/createConversazione")
	public String createConversazione(@RequestParam(value = "idConversazione") Long idConversazione,
			@RequestParam(value = "contenuto") String contenuto) throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		conversazioneService.createMessaggio(idUtente, idConversazione, contenuto);
		return "common/conversations-list";
	}

	@PostMapping("/updateConversazione")
	public String updateConversazione(@RequestParam(value = "idUtente") Long idUtenteContattato,
			@RequestParam(value = "nuovoMessaggio") String nuovoMessaggio) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		Long idUtente = Long.parseLong(id);
		conversazioneService.updateConversazione(idUtente, idUtenteContattato);
		return "common/conversations-list";
	}

}