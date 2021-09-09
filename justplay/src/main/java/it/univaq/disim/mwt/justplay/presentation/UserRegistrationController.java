package it.univaq.disim.mwt.justplay.presentation;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;
import it.univaq.disim.mwt.justplay.domain.Ruolo;
import it.univaq.disim.mwt.justplay.domain.Utente;
import it.univaq.disim.mwt.justplay.security.UserDetailsServiceImpl;

@Controller
@RequestMapping("/common/register")
public class UserRegistrationController {

	@Autowired
	private UtenteService service;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping
	public String showRegistrationForm(Model model) throws BusinessException {
		Utente utente = new Utente();
		model.addAttribute("utente", utente);
		return "/common/register";
	}

	@PostMapping
	public String SignUp(@ModelAttribute Utente nuovoUtente, Errors errors) throws BusinessException {
		//nuovoUtente.setNome("nome");
		//nuovoUtente.setCognome("cognome");
		//nuovoUtente.setPassword(passwordEncoder.encode(nuovoUtente.getPassword()));
        //nuovoUtente.setPassword(passwordEncoder.encode(nuovoUtente.getPassword()));
		//nuovoUtente.setDataNascita(LocalDate.of(2020, 1, 1));
		// Ruolo ruolo = new Ruolo();
		// ruolo.setId((long) 2);
		// ruolo.setNome("Amministratore");
		// ruolo.setDescrizione("Amministratore");
		// Set<Ruolo> ruoli = new Set<Ruolo>();
		// nuovoUtente.setRuoli("cognome");
		if (errors.hasErrors()) {
			return "/common/register";
		}
		service.save(nuovoUtente);
		return "/common/login";
	}

}
