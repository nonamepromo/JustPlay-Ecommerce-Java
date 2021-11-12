package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping
	public String showRegistrationForm(Model model) throws BusinessException {
		model.addAttribute("utente", new Utente());
		return "/common/register";
	}

	@PostMapping
	public String SignUp(@ModelAttribute Utente utente, Model model) throws BusinessException {
		try {
			if (service.existsByUsername(utente.getUsername())) {
				model.addAttribute("error", "username");
				throw new BusinessException("L'username è già usato");
			} else if (service.existsByEmail(utente.getEmail())) {
				model.addAttribute("error", "email");
				throw new BusinessException("Email già in uso");
			} else if (!passwordEncoder.matches(utente.getPassword(),
					passwordEncoder.encode(utente.getMatchingPassword()))) {
				throw new BusinessException("Le password non coincidono");
			} else {
				utente.setPassword(passwordEncoder.encode(utente.getPassword()));
				service.save(utente);
				return "/common/login";
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
