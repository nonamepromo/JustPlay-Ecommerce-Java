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
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.business.VideogiochiMongoDBService;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.business.impl.jpa.repository.UtenteRepository;
import it.univaq.disim.mwt.justplay.domain.Conversazione;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;
import it.univaq.disim.mwt.justplay.domain.VideogiocoDesiderato;
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

	@Autowired
	private UtenteService utenteService;

	/*
	 * Prende in argomento l'indice della paginazione e la piattaforma e restituisce
	 * la lista di videogiochi che rispettano i parametri
	 */
	@GetMapping(value = "/list", params = { "platform", "index" })
	public String listWithPlatform(@RequestParam(value = "platform", defaultValue = "all") String platform,
			@RequestParam(value = "index", defaultValue = "1") int index, Model model) throws BusinessException {
		service.addGameFromMdb();
		int videogiochiCount = service.getVideogiochiCount(platform);
		int numberOfIndexes = videogiochiCount / 6
				+ ((videogiochiCount % 6 == 0) ? 0 : 1);
		model.addAttribute("videogiochiCount", numberOfIndexes);
		model.addAttribute("platform", platform);
		model.addAttribute("videogiochi", service.findByPlatform(platform, index));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() != "anonymousUser") {
			Long idUtente = Long.parseLong(authentication.getPrincipal().toString());
			getWishlist(model, idUtente);
			getPlayedlist(model, idUtente);
		}
		return "videogiochi/list";

	}

	/*
	 * Viene richiamato se è stata effettuata una ricerca testuale, prende gli
	 * stessi argomenti di listWithPlatform() più la stringa digitata nel campo di
	 * ricerca. Se è una stringa vuota, viene restituita la lista completa di
	 * videogiochi
	 */
	@GetMapping(value = "/list", params = { "platform", "index", "searchString" })
	public String listWithPlatformResearched(@RequestParam(value = "platform", defaultValue = "all") String platform,
			@RequestParam(value = "index", defaultValue = "1") int index,
			@RequestParam(value = "searchString") String searchString, Model model) throws BusinessException {
		service.addGameFromMdb();
		List<Videogioco> videogiochi = new ArrayList<Videogioco>();
		int numberOfIndexes = 0;
		if (searchString.equals("")) {
			videogiochi = service.findByPlatform(platform, index);
			model.addAttribute("videogiochi", videogiochi);
			numberOfIndexes = service.getVideogiochiCount(platform) / 6
					+ ((service.getVideogiochiCount(platform) % 6 == 0) ? 0 : 1);
		} else {
			model.addAttribute("isResearch", true);
			model.addAttribute("searchString", searchString);
			videogiochi = service.findByPlatformResearched(platform, index, searchString);
			model.addAttribute("videogiochi", videogiochi);
			numberOfIndexes = service.getVideogiochiSearchedCount(searchString) / 6
					+ ((service.getVideogiochiSearchedCount(searchString) % 6 == 0) ? 0 : 1);
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

		model.addAttribute("wishList", service.getWishlist(utenteService.findById(idUtente).get()));
	}

	public void getPlayedlist(Model model, Long idUtente) throws BusinessException {
		model.addAttribute("playedList", service.getPlayedlist(utenteService.findById(idUtente).get()));
	}

	public void getSellinglist(Long idVideogioco, Model model) throws BusinessException {
		model.addAttribute("sellingList", service.getSellinglist(idVideogioco));
	}

	public void platform(@RequestParam("idVideogioco") Long idVideogioco, Model model, String[] ps4Urls,
			String[] xboxUrls, String[] pcUrls) throws BusinessException {
		model.addAttribute("ps4Urls", ps4Urls);
		model.addAttribute("xboxUrls", xboxUrls);
		model.addAttribute("pcUrls", pcUrls);
	}

	/*
	 * Prende in argomento un id e restituisce il videogioco corrispondente che
	 * viene aggiunto come attributo al Model. Inoltre, vengono aggiunti al Model
	 * anche i seguenti attributi: - Booleano che indica se il videogioco è nella
	 * lista dei videogiochi piaciuti o non piaciuti dell'utente connesso - Link
	 * appartenenti agli store in cui è presente il videogioco - Lista di vendite di
	 * quel gioco da parte di tutti gli utenti
	 */
	@GetMapping("/details")
	public String details(@RequestParam("idVideogioco") Long idVideogioco, Model model) throws BusinessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() != "anonymousUser") {
			Utente utente = Utility.getUtente();
			model.addAttribute("idUtente", utente.getId());
			getWishlist(model, utente.getId());
			getPlayedlist(model, utente.getId());
			getCompleteSellinglist(model, idVideogioco, utente.getId());
			VideogiocoPiaciuto videogiocoPiaciuto = service.findLikedGame(utente,
					service.findVideogiocoByID(idVideogioco));
			if (videogiocoPiaciuto != null) {
				model.addAttribute("piaciuto", videogiocoPiaciuto.isPiaciuto());
			}
		}
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		// amazonService.mongoAmazon();
		// gamestopService.mongoGamestop();
		model.addAttribute("amazon", amazonService.findAllByFkVideogioco(idVideogioco));
		model.addAttribute("gamestop", gamestopService.findAllByFkVideogioco(idVideogioco));
		model.addAttribute("videogioco", videogioco);
		model.addAttribute("idVideogioco", idVideogioco);
		VideogiocoInVendita videogiocoInVendita = new VideogiocoInVendita();
		model.addAttribute("videogioco_in_vendita", videogiocoInVendita);
		model.addAttribute("videogiochi_in_vendita", service.findAllVendita(idVideogioco));
		getSellinglist(idVideogioco, model);
		countLikedGameByFkVideogioco(idVideogioco, model);
		platform(idVideogioco, model, null, null, null);
		return "videogiochi/details";
	}

	public void countLikedGameByFkVideogioco(Long fkVideogioco, Model model) throws BusinessException {
		model.addAttribute("countPiaciuti",
				service.countLikedGameByVideogioco(service.findVideogiocoByID(fkVideogioco), true));
		model.addAttribute("countNonPiaciuti",
				service.countLikedGameByVideogioco(service.findVideogiocoByID(fkVideogioco), false));
	}

	/*
	 * Se non esiste già, viene creato un oggetto Conversazione con gli id
	 * dell'utente connesso e l'id dell'utente che ha messo in vendita il
	 * videogioco. Se esiste già, viene aggiunto l'oggetto come attributo nel Model
	 */
	@RequestMapping(value = "/createConversazione", method = RequestMethod.GET)
	public String createConversazione(@RequestParam("fkUtente") Long fkUtente, Long idUtente, Model model,
			RedirectAttributes redirAttrs) throws BusinessException {
		idUtente = Utility.getUtente().getId();
		Conversazione conversazione = new Conversazione();
		// Verifico se è lo stesso utente
		if (idUtente == fkUtente) {
			redirAttrs.addFlashAttribute("error", "");
			return "redirect:/videogiochi/list?platform=all&index=1";
		}
		if (conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(idUtente, fkUtente) != null) {
			conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(idUtente, fkUtente);
		} else if (conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(fkUtente, idUtente) != null) {
			conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(fkUtente, idUtente);
		} else {
			conversazioneService.createConversazione(idUtente, fkUtente);
			conversazione = conversazioneService.findIdConversazionByFkUtente1AndFkUtente2(idUtente, fkUtente);
		}
		return "redirect:/common/conversation?idConversazione=" + conversazione.getIdConversazione();
	}

	// Questo aggiunge un gioco alla wishlist nella view list utilizzando ajax
	@PostMapping("/addGameToWishlist")
	public String addGameToWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco, Model model)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.addGameToWishlist(videogioco, utente);
		return "redirect:/videogiochi/list?platform=all&index=1";
	}

	// Questo aggiunge un gioco alla wishlist da details se il param ricevuto è save
	@RequestMapping(value = "/addGameToWishlist", method = RequestMethod.GET, params = "action=save")
	public String addGameToWishlistDetails(@RequestParam(value = "idVideogioco") Long idVideogioco, Model model)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.addGameToWishlist(videogioco, utente);
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}

	// Questo aggiunge un gioco alla wishlist da details se il param ricevuto è
	// delete
	@RequestMapping(value = "/addGameToWishlist", method = RequestMethod.GET, params = "action=delete")
	public String removeGameFromWishlistDetails(@RequestParam(value = "idVideogioco") Long idVideogioco, Model model)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.removeGameFromWishlist(videogioco, utente);
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}

	// Questo aggiunge un gioco alla playedlist nella view list utilizzando ajax
	@PostMapping("/addGameToPlayedlist")
	public String addGameToPlayedlist(@RequestParam(value = "idVideogioco") Long idVideogioco, Model model)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.addGameToPlayedlist(videogioco, utente);
		return "redirect:/videogiochi/list?platform=all&index=1";
	}

	// Questo aggiunge un gioco alla playedlist da details se il param ricevuto è
	// save
	@RequestMapping(value = "/addGameToPlayedlist", method = RequestMethod.GET, params = "action=save")
	public String addGameToPlayedlistDetails(@RequestParam(value = "idVideogioco") Long idVideogioco, Model model)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		// service.addGameToPlayedlist(idVideogioco, idUtente);
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.addGameToPlayedlist(videogioco, utente);
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}

	// Questo aggiunge un gioco alla playedlist da details se il param ricevuto è
	// delete
	@RequestMapping(value = "/addGameToPlayedlist", method = RequestMethod.GET, params = "action=delete")
	public String removeGameFromPlayedlistDetails(@RequestParam(value = "idVideogioco") Long idVideogioco, Model model)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		// service.removeGameFromPlayedlist(idVideogioco, idUtente);
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.removeGameFromPlayedlist(videogioco, utente);
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
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
	public String addGameToLikedlist(@RequestParam Long idVideogioco, @RequestParam boolean piaciuto)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		VideogiocoPiaciuto videogiocoPiaciuto = service.findLikedGame(utente, videogioco);
		if (videogiocoPiaciuto != null) {
			if (videogiocoPiaciuto.isPiaciuto() == piaciuto) {
				service.removeGameFromLikedlist(utente, videogioco);
			} else {
				service.removeGameFromLikedlist(utente, videogioco);
				service.addGameToLikedlist(videogioco, piaciuto);
			}
		} else {
			service.addGameToLikedlist(videogioco, piaciuto);
		}
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}

	@PostMapping("/removeGameFromWishlist")
	public String removeGameFromWishlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.removeGameFromWishlist(videogioco, utente);
		return "redirect:/videogiochi/list?platform=all&index=1";
	}

	@PostMapping("/removeGameFromPlayedlist")
	public String removeGameFromPlayedlist(@RequestParam(value = "idVideogioco") Long idVideogioco)
			throws BusinessException {
		Utente utente = Utility.getUtente();
		Videogioco videogioco = service.findVideogiocoByID(idVideogioco);
		service.removeGameFromPlayedlist(videogioco, utente);
		return "redirect:/videogiochi/list?platform=all&index=1";
	}
	@RequestMapping(value = "/removeGameFromSellinglist", method = RequestMethod.GET)
	public String removeGameFromSellinglist(@RequestParam Long idVideogioco, @RequestParam Long idVenduto)
			throws BusinessException {
		Long idUtente = Utility.getUtente().getId();
		service.removeGameFromSellinglist(idVideogioco, idUtente, idVenduto);
		return "redirect:/videogiochi/details?idVideogioco=" + idVideogioco;
	}

	public void getCompleteSellinglist(Model model, Long idVideogioco, Long IdUtente) throws BusinessException {
		List<VideogiocoInVendita> videogiochiInVendita = service.getCompleteSellinglist(idVideogioco, IdUtente);
		model.addAttribute("videogiochi_utente_in_vendita", videogiochiInVendita);
	}

	@PostMapping("/editSellingGame")
	public String editSellingGame(@ModelAttribute VideogiocoInVendita videogiocoInVendita,
			RedirectAttributes redirAttrs) throws BusinessException {
		service.saveSellingGame(videogiocoInVendita);
		redirAttrs.addFlashAttribute("success", "");
		return "redirect:/videogiochi/details?idVideogioco=" + videogiocoInVendita.getFkVideogioco();
	}

}
