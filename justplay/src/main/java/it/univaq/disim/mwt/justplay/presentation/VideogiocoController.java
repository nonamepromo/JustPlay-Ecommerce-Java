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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Controller
@RequestMapping(value = "videogiochi", method = RequestMethod.POST)
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;

	@GetMapping("/list")
	public String showAll(@RequestParam(value = "index", defaultValue = "1") int index, Model model)
			throws BusinessException {
		model.addAttribute("videogiochi", service.findAll(3 * index));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() != "anonymousUser") {

			Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
			getWishlist(model, idUtente);
			getPlayedlist(model, idUtente);
		}
		return "videogiochi/list";

	}

	@GetMapping("/wishlist")
	public String wishlist(Model model) throws BusinessException {
		return "videogiochi/wishlist";
	}

	public void getWishlist(Model model, Long idUtente) throws BusinessException {

		model.addAttribute("wishList", service.getWishlist(idUtente));
	}

	public void getPlayedlist(Model model, Long idUtente) throws BusinessException {

		model.addAttribute("playedList", service.getPlayedlist(idUtente));
	}

	public void getSellinglist(Model model, Long idVideogioco) throws BusinessException {

		model.addAttribute("sellingList", service.getSellinglist(idVideogioco));
	}

//	@GetMapping("/list")
//	public String getWishList(Model model) throws BusinessException {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String id = authentication.getPrincipal().toString();
//		
//		Long idUtente = Long.parseLong(id);
//	    model.addAttribute("wishList", service.getWishlist(idUtente));
//	    return "videogiochi/list";
//	}

//	@GetMapping("/findallpaginated")
//	public ResponseEntity<List<Videogioco>> findAllPaginated()
//			throws BusinessException {
//		return service.findAllVideogiochiPaginated();
//	}

	@GetMapping("/details")
	public String details(@RequestParam("id") Long id, Model model) throws BusinessException {
		Videogioco videogioco = service.findVideogiocoByID(id);
		model.addAttribute("videogioco", videogioco);

		// Long idVideogioco = Long.parseLong(videogioco.toString());
		// getSellinglist(model, idVideogioco);

		String[] ps4Urls = null;
		String[] xboxUrls = null;
		String[] pcUrls = null;
		if (videogioco.getPs4Url() != null) {
			ps4Urls = videogioco.getPs4Url().split(";");
		}
		;
		if (videogioco.getXboxUrl() != null) {
			xboxUrls = videogioco.getXboxUrl().split(";");
		}
		;
		if (videogioco.getPcUrl() != null) {
			pcUrls = videogioco.getPcUrl().split(";");
		}
		;
		model.addAttribute("ps4Urls", ps4Urls);
		model.addAttribute("xboxUrls", xboxUrls);
		model.addAttribute("pcUrls", pcUrls);

		return "videogiochi/details";
	}

	@PostMapping("/addGameToWishlist")
	public String addGameToWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco,
			RedirectAttributes redirAttrs) throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.addGameToWishlist(idVideogioco, idUtente);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/videogiochi/list";
	}

	@PostMapping("/addGameToPlayedlist")
	public String addGameToPlayedlist(@RequestParam(value = "idVideogioco") Long idVideogioco,
			RedirectAttributes redirAttrs) throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.addGameToPlayedlist(idVideogioco, idUtente);
		redirAttrs.addFlashAttribute("success", "");
		return "/videogiochi/list";
	}

	@PostMapping("/addGameToSellinglist")
	public String addGameToSellinglist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.addGameToSellinglist(idVideogioco, idUtente);
		return "videogiochi/list";
	}

	@PostMapping("/addGameToSellinglistProva")
	public String addGameToSellinglistProva(
			@ModelAttribute("videogiochi_in_vendita") VideogiocoInVendita nuovoVideogiocoInVendita,
			@RequestParam(value = "idVideogioco") Long idVideogioco, RedirectAttributes redirAttrs)
			throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Utente utente = Utility.getUtente();

		nuovoVideogiocoInVendita.setFk_utente(utente.getId());
		nuovoVideogiocoInVendita.setFk_videogioco(idVideogioco);

		service.addGameToSellinglistProva(nuovoVideogiocoInVendita);
		redirAttrs.addFlashAttribute("success", "");
		return "videogiochi/details";
	}

	@PostMapping("/removeGameFromWishlist")
	public String removeGameFromWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.removeGameFromWishlist(idVideogioco, idUtente);

		return "videogiochi/list";
	}

	@PostMapping("/removeGameFromPlayedlist")
	public String removeGameFromPlayedlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.removeGameFromPlayedlist(idVideogioco, idUtente);
		return "videogiochi/list";
	}

	@PostMapping("/removeGameFromSellinglist")
	public String removeGameFromSellinglist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.removeGameFromSellinglist(idVideogioco, idUtente);
		return "videogiochi/list";
	}

	// @GetMapping("/checkIfGameIsDesidered")
	// public ResponseEntity<String>
	// checkIfGameIsDesidered(@RequestParam("idUtente") Long idUtente,
	// @RequestParam("idVideogioco") Long idVideogioco) throws BusinessException {
	// boolean exist = service.checkIfGameIsDesidered(idUtente, idVideogioco);
	// String result = null;
	// if(exist) {
	// result = "SUCCESS";
	// }
	// else {
	// result = "FAIL";
	// }
	// return ResponseEntity.ok(result);

	// }

	@GetMapping("/create")
	public String createStart(Model model) {
		model.addAttribute("videogioco", new Videogioco());
		return "videogiochi/form";
	}

	@GetMapping("/delete")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		// Videogioco videogioco = service.findVideogiocoByID(id);
		// model.addAttribute("videogioco", videogioco);
		return "videogiochi/form";
	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute("videogioco") Videogioco videogioco) throws BusinessException {
		service.deleteVideogioco(videogioco);
		return "redirect:/videogiochi/list";
	}

}
