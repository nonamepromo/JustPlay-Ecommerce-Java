package it.univaq.disim.mwt.justplay.presentation.validator;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	@Autowired
	private UtenteService utenteService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		try {
			return !utenteService.existsByEmail(value);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
