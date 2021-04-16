package it.univaq.disim.mwt.justplay.presentation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.RequestGrid;
import it.univaq.disim.mwt.justplay.business.ResponseGrid;
import it.univaq.disim.mwt.justplay.business.VideogiocoService;
import it.univaq.disim.mwt.justplay.domain.Videogioco;

@Controller
@RequestMapping("videogiochi")
public class VideogiocoController {

	@Autowired
	private VideogiocoService service;

	@GetMapping("/list")
	public String list() {
		return "videogiochi/list";
	}

	@PostMapping("/findallpaginated")
	public @ResponseBody ResponseGrid<Videogioco> findAllPaginated(@RequestBody RequestGrid requestGrid)
			throws BusinessException {
		return service.findAllVideogiochiPaginated(requestGrid);
	}
	
	@GetMapping("/create")
	public String createStart(Model model) {
		model.addAttribute("videogioco", new Videogioco());
		return "videogiochi/form";
	}
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("videogioco") Videogioco videogioco, Errors errors) throws BusinessException {
		//Prendere e validare i dati dalla form e costruire un oggetto di tipo Videogioco
		//Se la validazione va a buon fine Invocare la logica di business. Altrimenti far vedere la form con gli errori
		//Fare il "forward" alla vista
		if (errors.hasErrors()) {
			return "videogiochi/form";
		}
		service.createVideogioco(videogioco);
		return "redirect:/videogiochi/list";
	}

	@GetMapping("/update")
	public String updateStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Videogioco videogioco = service.findVideogiocoByID(id);
		model.addAttribute("videogioco", videogioco);
		return "videogiochi/form";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("videogioco") Videogioco videogioco, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "videogiochi/form";
		}
		service.updateVideogioco(videogioco);
		return "redirect:/videogiochi/list";
	}
	
	@GetMapping("/delete")
	public String deleteStart(@RequestParam("id") Long id, Model model) throws BusinessException {
		Videogioco videogioco = service.findVideogiocoByID(id);
		model.addAttribute("videogioco", videogioco);
		return "videogiochi/form";
	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute("videogioco") Videogioco videogioco) throws BusinessException {
		service.deleteVideogioco(videogioco);
		return "redirect:/videogiochi/list";
	}

	
}
