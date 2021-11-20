package it.univaq.disim.mwt.justplay.presentation;

import java.util.List;
import java.util.Optional;
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
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
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

	@ModelAttribute
	public void getUtente(Model model) throws BusinessException {
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			Utente utente = utenteService.findById(Utility.getUtente().getId()).get();
			model.addAttribute("utente", utente);
		}
	}

	// @GetMapping(value = "/list", params = { "size", "page" })
	// public String list(Model model, @RequestParam("page") Optional<Integer> page,
	// @RequestParam("size") Optional<Integer> size) throws BusinessException {

	// int currentPage = page.orElse(1);
	// int pageSize = size.orElse(5);

	// Page<Videogioco> videogiochiPage = service.findAll(PageRequest.of(currentPage
	// - 1, pageSize));

	// model.addAttribute("videogiochiPage", videogiochiPage);

	// int totalPages = videogiochiPage.getTotalPages();
	// if (totalPages > 0) {
	// List<Integer> pageNumbers = IntStream.rangeClosed(1,
	// totalPages).boxed().collect(Collectors.toList());
	// model.addAttribute("pageNumbers", pageNumbers);
	// }

	// return "videogiochi/list";

	// }

	@GetMapping("/gameFromWishlist")
	public String gameFromWishlist(@RequestParam("uri") String request, @ModelAttribute("videogioco") Videogioco videogioco,
			@ModelAttribute("utente") Utente utente) throws BusinessException {
		utenteService.desiderato(utente, videogioco);
		if(request.contains("details")) {
			return "redirect:/videogiochi/details?idVideogioco=" + videogioco.getId();
		}
		return "redirect:/videogiochi/list?size=6&page=1";
	}

	@GetMapping("/gameFromPlayedlist")
	public String gameFromPlayedlist(@RequestParam("uri") String request, @ModelAttribute("videogioco") Videogioco videogioco,
			@ModelAttribute("utente") Utente utente) throws BusinessException {
		utenteService.giocato(utente, videogioco);
		if(request.contains("details")) {
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
	
	/*
	@GetMapping("/gameLiked")
	public String addGameToLikedlist(@RequestParam boolean piaciuto,
			@ModelAttribute("videogioco") Videogioco videogioco,
			@ModelAttribute("utente") Utente utente) throws BusinessException {		
		service.addGameToLikedlist(videogioco, utente, piaciuto);
		return "redirect:/videogiochi/details?idVideogioco=" + videogioco.getId();
	}
	*/
	
	@PostMapping("/addGameToSellinglist")
	public String addGameToSellinglist(@ModelAttribute VideogiocoInVendita videogiocoInVendita,
			@ModelAttribute("videogioco") Videogioco videogioco,
			@ModelAttribute("utente") Utente utente,
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
	public String details(@RequestParam("idVideogioco") Long idVideogioco,
			@ModelAttribute("utente") Utente utente,
			Model model) throws BusinessException {
		
		model.addAttribute("videogioco", service.findById(idVideogioco));
		model.addAttribute("pincodes", service.findAllPincodes());

		/*
		for(VideogiocoPiaciuto videogiocoPiaciuto : utente.getVideogiochiPiaciuti()) {
			if(videogiocoPiaciuto.getVideogioco().getId() == idVideogioco) {
				model.addAttribute("videogiocoPiaciuto", videogiocoPiaciuto.isPiaciuto());
			}
		} 
		
		
		for(VideogiocoPiaciuto videogiocoPiaciuto : service.findById(idVideogioco).getVideogiochiPiaciuti()) {
			if(videogiocoPiaciuto.getUtente().equals(utente)) {
				model.addAttribute("videogiocoPiaciuto", videogiocoPiaciuto);
			}
		}
		*/
		
		return "videogiochi/details";
	}

}
