package it.univaq.disim.mwt.justplay.presentation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import it.univaq.disim.mwt.justplay.business.CommentoService;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Commento;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Controller
@RequestMapping("/videogiochi")
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private CommentoService commentoService;

	@Autowired
	private ConversazioneService conversazioneService;

	@ModelAttribute
	public void getUtente(Model model) throws BusinessException {
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			Utente utente = utenteService.findById(Utility.getUtente().getId()).get();
			model.addAttribute("utente", utente);
		}
	}

	@GetMapping("/gameFromWishlist")
	public String gameFromWishlist(@RequestParam("uri") String request,
			@ModelAttribute("videogioco") Videogioco videogioco, @ModelAttribute("utente") Utente utente)
			throws BusinessException {
		utenteService.desiderato(utente, videogioco);
		if (request.contains("details")) {
			return "redirect:/videogiochi/details?idVideogioco=" + videogioco.getId();
		}
		return "redirect:/videogiochi/list?size=6&page=1";
	}

	@GetMapping("/gameFromPlayedlist")
	public String gameFromPlayedlist(@RequestParam("uri") String request,
			@ModelAttribute("videogioco") Videogioco videogioco, @ModelAttribute("utente") Utente utente)
			throws BusinessException {
		utenteService.giocato(utente, videogioco);
		if (request.contains("details")) {
			return "redirect:/videogiochi/details?idVideogioco=" + videogioco.getId();
		}
		return "redirect:/videogiochi/list?size=6&page=1";
	}

	@GetMapping("/gameLiked")
	public String addGameToLikedlist(@ModelAttribute("videogioco") Videogioco videogioco,
			@ModelAttribute("utente") Utente utente) throws BusinessException {
		utenteService.piaciuto(utente, videogioco);
		return "redirect:/videogiochi/details?idVideogioco=" + videogioco.getId();
	}

	@GetMapping("/gameUnliked")
	public String addGameToUnlikedlist(@ModelAttribute("videogioco") Videogioco videogioco,
			@ModelAttribute("utente") Utente utente) throws BusinessException {
		utenteService.nonPiaciuto(utente, videogioco);
		return "redirect:/videogiochi/details?idVideogioco=" + videogioco.getId();
	}

	@PostMapping("/addGameToSellinglist")
	public String addGameToSellinglist(@ModelAttribute VideogiocoInVendita videogiocoInVendita,
			@ModelAttribute("videogioco") Videogioco videogioco, @ModelAttribute("utente") Utente utente,
			RedirectAttributes redirAttrs) throws BusinessException {
		service.addGameToSellinglist(videogiocoInVendita, utente, videogioco);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/videogiochi/details?idVideogioco=" + videogioco.getId();
	}

	@PostMapping("/editSellingGame")
	public String editSellingGame(@ModelAttribute VideogiocoInVendita nuovoVideogiocoInVendita,
			RedirectAttributes redirAttrs) throws BusinessException {
		service.editSelledGame(nuovoVideogiocoInVendita);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/videogiochi/details?idVideogioco=" + nuovoVideogiocoInVendita.getVideogioco().getId();
	}

	@GetMapping("/removeGameFromSellinglist")
	public String removeGameFromSellinglist(
			@ModelAttribute("videogiocoInVendita") VideogiocoInVendita videogiocoInVendita) throws BusinessException {
		service.removeGameFromSellinglist(videogiocoInVendita);
		return "redirect:/videogiochi/details?idVideogioco=" + videogiocoInVendita.getVideogioco().getId();
	}

	@GetMapping("/list")
	public String list(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @ModelAttribute("searchString") String searchString,
			@RequestParam(required = false, value = "platform", defaultValue = "") String platform)
			throws BusinessException {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Page<Videogioco> videogiochiPage = service.filterVideogiochi(PageRequest.of(currentPage - 1, pageSize),
				searchString, platform);

		model.addAttribute("videogiochiPage", videogiochiPage);

		int totalPages = videogiochiPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "videogiochi/list";
	}

	@GetMapping("/details")
	public String details(@RequestParam("idVideogioco") Long idVideogioco, @ModelAttribute("utente") Utente utente,
			Model model) throws BusinessException {

		model.addAttribute("videogioco", service.findById(idVideogioco));
		model.addAttribute("pincodes", service.findAllPincodes());

		return "videogiochi/details";
	}

	@PostMapping("/addCommento")
	public String addCommento(@ModelAttribute("utente") Utente utente, @ModelAttribute("commento") Commento commento,
			Model model, RedirectAttributes redirAttrs) throws BusinessException {
		if (commento.getVideogioco().getCommenti().stream()
				.filter(o -> o.getUtente().getUsername().equals(utente.getUsername())).findFirst().isPresent()) {
					redirAttrs.addFlashAttribute("error", "");
					return "redirect:/videogiochi/details?idVideogioco=" + commento.getVideogioco().getId();
		} else {
			commento.setUtente(utente);
			commentoService.addCommento(commento);
		}
		return "redirect:/videogiochi/details?idVideogioco=" + commento.getVideogioco().getId();
	}

	@PostMapping("/deleteCommento")
	public String delete(@RequestParam("idCommento") Long idCommento, @RequestParam("idVideogioco") Long idVideogioco)
			throws BusinessException {
		commentoService.deleteCommento(idCommento);
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}

	@GetMapping("/createConversazione")
	public String createConversazione(@RequestParam("usernamePartecipante") String usernamePartecipante,
			@ModelAttribute("utente") Utente utente, @ModelAttribute("conversazione") Conversazione conversazione,
			RedirectAttributes redirAttrs) throws BusinessException {
		if (usernamePartecipante.equals(utente.getUsername())) {
			redirAttrs.addFlashAttribute("error", "");
			return "redirect:/videogiochi/list";
		} else {
			Set<String> partecipanti = new HashSet<String>();
			partecipanti.add(usernamePartecipante);
			partecipanti.add(utente.getUsername());
			if (conversazioneService.findConversazioneByUsernames(partecipanti) != null) {
				return "redirect:/common/conversation?usernamePartecipante=" + usernamePartecipante;
			} else {
				conversazione.setPartecipanti(partecipanti);
				conversazioneService.addOrUpdateConversazione(conversazione);
				return "redirect:/common/conversation?usernamePartecipante=" + usernamePartecipante;
			}
		}
	}

}
