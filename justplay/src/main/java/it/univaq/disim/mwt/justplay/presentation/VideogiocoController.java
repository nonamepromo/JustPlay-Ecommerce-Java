package it.univaq.disim.mwt.justplay.presentation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Controller
@RequestMapping("/videogiochi")
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;

	@ModelAttribute
	public void getUtente(Model model) throws BusinessException {
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			Utente utente = Utility.getUtente();
			model.addAttribute("utente", utente);
		}
	}

	// @GetMapping(value = "/list", params = { "size", "page" })
	// public String list(Model model, @RequestParam("page") Optional<Integer> page,
	// 		@RequestParam("size") Optional<Integer> size) throws BusinessException {

	// 	int currentPage = page.orElse(1);
	// 	int pageSize = size.orElse(5);

	// 	Page<Videogioco> videogiochiPage = service.findAll(PageRequest.of(currentPage - 1, pageSize));

	// 	model.addAttribute("videogiochiPage", videogiochiPage);

	// 	int totalPages = videogiochiPage.getTotalPages();
	// 	if (totalPages > 0) {
	// 		List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
	// 		model.addAttribute("pageNumbers", pageNumbers);
	// 	}

	// 	return "videogiochi/list";

	// }

	@GetMapping("/list")
	public String list(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @ModelAttribute("searchString") String searchString,
			@RequestParam(required=false, value = "platform", defaultValue="") String platform) throws BusinessException {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Page<Videogioco> videogiochiPage = service.filterVideogiochi(PageRequest.of(currentPage - 1, pageSize), searchString, platform);

		model.addAttribute("videogiochiPage", videogiochiPage);

		int totalPages = videogiochiPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "videogiochi/list";
	}

	@GetMapping("/details")
	public String details(@RequestParam("idVideogioco") Long idVideogioco, Model model) throws BusinessException {
		model.addAttribute("videogioco", service.findById(idVideogioco));

		return "videogiochi/details";
	}

}
