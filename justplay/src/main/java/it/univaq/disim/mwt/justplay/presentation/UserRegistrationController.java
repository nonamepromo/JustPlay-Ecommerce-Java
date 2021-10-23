package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.domain.Utente;

@Controller
@RequestMapping("/common/register")
public class UserRegistrationController {

	@Autowired
	private UtenteService service;

	@GetMapping
	public String showRegistrationForm(Model model) throws BusinessException {
		Utente utente = new Utente();
		model.addAttribute("utente", utente);
		return "/common/register";
	}

	@PostMapping
	public String SignUp(@ModelAttribute Utente nuovoUtente, Errors errors) throws BusinessException {
		if (errors.hasErrors()) {
			return "/common/register";
		}
		service.save(nuovoUtente);
		return "/common/login";
	}

}
