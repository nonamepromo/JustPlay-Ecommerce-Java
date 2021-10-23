package it.univaq.disim.mwt.justplay.presentation;

import java.util.ArrayList;
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

import it.univaq.disim.mwt.justplay.business.AmazonService;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.ConversazioneService;
import it.univaq.disim.mwt.justplay.business.GamestopService;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoInVendita;
import it.univaq.disim.mwt.justplay.domain.VideogiocoPiaciuto;

@Controller
@RequestMapping(value = "/videogiochi", method = RequestMethod.POST)
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;

	@Autowired
	private AmazonService amazonService;

	@Autowired
	private GamestopService gamestopService;

	@Autowired
	private ConversazioneService conversazioneService;

	@GetMapping(value = "/list", params = { "platform", "index" })
	public String listWithPlatform(@RequestParam(value = "platform", defaultValue = "all") String platform,
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

	@GetMapping(value = "/list", params = { "platform", "index", "searchString" })
	public String listWithPlatformResearched(@RequestParam(value = "platform", defaultValue = "all") String platform,
			@RequestParam(value = "index", defaultValue = "1") int index,
			@RequestParam(value = "searchString") String searchString, Model model) throws BusinessException {
		List<Videogioco> videogiochi = new ArrayList<Videogioco>();
		int numberOfIndexes = 0;
		if (searchString == "") {
			videogiochi = service.findByPlatform(platform, index);
			model.addAttribute("videogiochi", videogiochi);
			numberOfIndexes = service.getVideogiochiCount(platform) / 3
					+ ((service.getVideogiochiCount(platform) % 3 == 0) ? 0 : 1);
		} else {
			model.addAttribute("isResearch", true);
			model.addAttribute("searchString", searchString);
			videogiochi = service.findByPlatformResearched(platform, index, searchString);
			model.addAttribute("videogiochi", videogiochi);
			numberOfIndexes = service.getVideogiochiSearchedCount(searchString) / 3
			+ ((service.getVideogiochiSearchedCount(searchString) % 3 == 0) ? 0 : 1);
		}

		model.addAttribute("videogiochiCount", numberOfIndexes);
		model.addAttribute("platform", platform);
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
	
	public void getLikedGame(Model model, Long idUtente, Long idVideogioco) throws BusinessException {
		model.addAttribute("likedGame", service.findLikedGame(idUtente, idVideogioco));
	}

	public void platform(@RequestParam("idVideogioco") Long idVideogioco, Model model, String[] ps4Urls,
			String[] xboxUrls, String[] pcUrls) throws BusinessException {
		model.addAttribute("ps4Urls", ps4Urls);
		model.addAttribute("xboxUrls", xboxUrls);
		model.addAttribute("pcUrls", pcUrls);
	}

	@GetMapping("/details")
	public String details(@RequestParam("idVideogioco") Long idVideogioco, Model model) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() != "anonymousUser") {
			Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
			model.addAttribute("idUtente", idUtente);
			VideogiocoPiaciuto videogiocoPiaciuto = service.findLikedGame(idUtente, idVideogioco);
			if(videogiocoPiaciuto != null) {
				model.addAttribute("piaciuto", videogiocoPiaciuto.isPiaciuto());
			};
		}
		//service.popolamentazione(); //Serve per sconfiggere il male
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		// amazonService.popolazione();
		// gamestopService.popolazione();
		// VEDERE DA RIGA 195 DI DETAILS.HTML
		model.addAttribute("amazon", amazonService.findAllByFkVideogioco(idVideogioco));
		model.addAttribute("gamestop", gamestopService.findAllByFkVideogioco(idVideogioco));
		model.addAttribute("videogioco", videogioco);
		model.addAttribute("idVideogioco", idVideogioco);
		VideogiocoInVendita videogiocoInVendita = new VideogiocoInVendita();
		model.addAttribute("videogioco_in_vendita", videogiocoInVendita);
		model.addAttribute("videogiochi_in_vendita", service.findAllVendita(idVideogioco));
		getSellinglist(model, idVideogioco);
		
		platform(idVideogioco, model, null, null, null);
		return "videogiochi/details";
	}

	@RequestMapping(value = "/createConversazione", method = RequestMethod.GET)
	public String createConversazione(@RequestParam("fkUtente") Long fkUtente, Long idUtente, Model model, RedirectAttributes redirAttrs)
			throws BusinessException {
		idUtente = Utility.getUtente().getId();
		if(idUtente == fkUtente) {
			redirAttrs.addFlashAttribute("error", "");
			return "redirect:/videogiochi/list?platform=all&index=1";
		}
		Conversazione conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(idUtente,
				fkUtente);
		if (conversazione != null) {
			conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(idUtente, fkUtente);
		}
		Conversazione conversazione2 = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(fkUtente,
				idUtente);
		if (conversazione2 != null) {
			conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(fkUtente, idUtente);
		}
		if (conversazione == null && conversazione2 == null) {
			conversazioneService.createConversazione(idUtente, fkUtente);
			conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(idUtente, fkUtente);
			if (conversazione == null) {
				conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(fkUtente, idUtente);
			}
		}
		return "redirect:/common/conversation?idConversazione=" + conversazione.getIdConversazione();
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
		return "redirect:/videogiochi/list?platform=all&index=1";
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
		return "redirect:/videogiochi/list?platform=all&index=1";
	}

	@PostMapping("/addGameToSellinglistProva")
	public String addGameToSellinglistProva(@ModelAttribute VideogiocoInVendita nuovoVideogiocoInVendita,
			@RequestParam(value = "idVideogioco") Long idVideogioco, RedirectAttributes redirAttrs)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		service.addGameToSellinglist(nuovoVideogiocoInVendita, idVideogioco, idUtente);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}
	
	@RequestMapping(value = "/addGameToLikedlist", method = RequestMethod.GET)
	public String addGameToLikedlist(@RequestParam Long idVideogioco, @RequestParam boolean piaciuto) throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		VideogiocoPiaciuto videogiocoPiaciuto = service.findLikedGame(idUtente, idVideogioco);
		if(videogiocoPiaciuto != null ) {
			if(videogiocoPiaciuto.isPiaciuto() == piaciuto) {
				service.removeGameFromLikedlist(idVideogioco, idUtente);
			}else {
				service.removeGameFromLikedlist(idVideogioco, idUtente);
				service.addGameToLikedlist(idVideogioco, idUtente, piaciuto);
			}
		}else {
			service.addGameToLikedlist(idVideogioco, idUtente, piaciuto);
		}
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}
			
	@PostMapping("/removeGameFromWishlist")
	public String removeGameFromWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		Long idUtente = Long.parseLong(id);
		service.removeGameFromWishlist(idVideogioco, idUtente);
		return "redirect:/videogiochi/list?platform=all&index=1";
	}

	@PostMapping("/removeGameFromPlayedlist")
	public String removeGameFromPlayedlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getPrincipal().toString();
		Long idUtente = Long.parseLong(id);
		service.removeGameFromPlayedlist(idVideogioco, idUtente);
		return "redirect:/videogiochi/list?platform=all&index=1";
	}

	@RequestMapping(value = "/removeGameFromSellinglist", method = RequestMethod.GET)
	public String removeGameFromSellinglist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		service.removeGameFromSellinglist(idVideogioco, idUtente);
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}

}
