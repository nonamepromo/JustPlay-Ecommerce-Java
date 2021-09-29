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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;

@Controller
@RequestMapping(value = "/videogiochi", method = RequestMethod.POST)
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;

	// DEVE RIMANERE COMMENTATO OPPURE NO?
	// @GetMapping(value = "/list", params = "index")
	// public String showAll(@RequestParam(value = "index", defaultValue = "1") int
	// index, Model model)
	// throws BusinessException {
	// int numberOfIndexes = service.getVideogiochiCount() / 3 +
	// ((service.getVideogiochiCount() % 3 == 0) ? 0 : 1);
	// model.addAttribute("videogiochiCount", numberOfIndexes);
	// model.addAttribute("videogiochi", service.findAll(3 * index));
	// Authentication authentication =
	// SecurityContextHolder.getContext().getAuthentication();
	// if (authentication.getPrincipal() != "anonymousUser") {

	// Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
	// getWishlist(model, idUtente);
	// getPlayedlist(model, idUtente);
	// }
	// return "videogiochi/list";

	// }

	@GetMapping(value = "/list", params = { "platform", "index" })
	public String listWithPlatform(@RequestParam(value = "platform") String platform,
			@RequestParam(value = "index", defaultValue = "1") int index, Model model) throws BusinessException {
		int numberOfIndexes = service.getVideogiochiCount(platform) / 3
				+ ((service.getVideogiochiCount(platform) % 3 == 0) ? 0 : 1);
		model.addAttribute("videogiochiCount", numberOfIndexes);
		model.addAttribute("platform", platform);
		// model.addAttribute("videogiochi", service.findByPlatform(platform, 3 *
		// index));
		model.addAttribute("videogiochi", service.findByPlatform(platform, index));
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

	// @GetMapping("/list")
	// public String getWishList(Model model) throws BusinessException {
	// Authentication authentication =
	// SecurityContextHolder.getContext().getAuthentication();
	// String id = authentication.getPrincipal().toString();
	//
	// Long idUtente = Long.parseLong(id);
	// model.addAttribute("wishList", service.getWishlist(idUtente));
	// return "videogiochi/list";
	// }

	// @GetMapping("/findallpaginated")
	// public ResponseEntity<List<Videogioco>> findAllPaginated()
	// throws BusinessException {
	// return service.findAllVideogiochiPaginated();
	// }

	public void platform(@RequestParam("idVideogioco") Long idVideogioco, Model model, String[] ps4Urls,
			String[] xboxUrls, String[] pcUrls) throws BusinessException {
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		// if (videogioco.getPs4Url() != null) {
		// ps4Urls = videogioco.getPs4Url().split(";");
		// }
		// ;
		// if (videogioco.getXboxUrl() != null) {
		// xboxUrls = videogioco.getXboxUrl().split(";");
		// }
		// ;
		// if (videogioco.getPcUrl() != null) {
		// pcUrls = videogioco.getPcUrl().split(";");
		// }
		model.addAttribute("ps4Urls", ps4Urls);
		model.addAttribute("xboxUrls", xboxUrls);
		model.addAttribute("pcUrls", pcUrls);
	}

	// @GetMapping("/list")
	// public String getWishList(Model model) throws BusinessException {
	// Authentication authentication =
	// SecurityContextHolder.getContext().getAuthentication();
	// String id = authentication.getPrincipal().toString();
	//
	// Long idUtente = Long.parseLong(id);
	// model.addAttribute("wishList", service.getWishlist(idUtente));
	// return "videogiochi/list";
	// }

	// @GetMapping("/findallpaginated")
	// public ResponseEntity<List<Videogioco>> findAllPaginated()
	// throws BusinessException {
	// return service.findAllVideogiochiPaginated();
	// }

	@GetMapping("/details")
	public String details(@RequestParam("idVideogioco") Long idVideogioco, Model model) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() != "anonymousUser") {
			Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
			model.addAttribute("idUtente", idUtente);
		}
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		model.addAttribute("videogioco", videogioco);
		model.addAttribute("idVideogioco", idVideogioco);
		VideogiocoInVendita videogiocoInVendita = new VideogiocoInVendita();
		model.addAttribute("videogioco_in_vendita", videogiocoInVendita);
		model.addAttribute("videogiochi_in_vendita", service.findAllVendita(idVideogioco));
		getSellinglist(model, idVideogioco);
		platform(idVideogioco, model, null, null, null);
		return "videogiochi/details";
	}

	@PostMapping("/addGameToWishlist")
	public String addGameToWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco,
			RedirectAttributes redirAttrs, Model model) throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.addGameToWishlist(idVideogioco, idUtente);
		List<Long> wishList = service.getWishlist(idUtente);
		model.addAttribute("wishList", wishList);
		redirAttrs.addFlashAttribute("success", "");
		return "videogiochi/list";
	}

	@PostMapping("/addGameToPlayedlist")
	public String addGameToPlayedlist(@RequestParam(value = "idVideogioco") Long idVideogioco,
			RedirectAttributes redirAttrs, Model model) throws BusinessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();

		Long idUtente = Long.parseLong(id);

		service.addGameToPlayedlist(idVideogioco, idUtente);
		List<Long> playedList = service.getPlayedlist(idUtente);
		model.addAttribute("playedList", playedList);
		redirAttrs.addFlashAttribute("success", "");
		return "videogiochi/list";
	}

	@PostMapping("/addGameToSellinglistProva")
	public String addGameToSellinglistProva(@ModelAttribute VideogiocoInVendita nuovoVideogiocoInVendita,
			@RequestParam(value = "idVideogioco") Long idVideogioco, RedirectAttributes redirAttrs)
			throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		Long idUtente = Long.parseLong(id);
		service.addGameToSellinglist(nuovoVideogiocoInVendita, idVideogioco, idUtente);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
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
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
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
		// service.deleteVideogioco(videogioco);
		return "redirect:/videogiochi/list";
	}

}
