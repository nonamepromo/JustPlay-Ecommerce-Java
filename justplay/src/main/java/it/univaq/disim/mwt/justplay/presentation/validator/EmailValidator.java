package it.univaq.disim.mwt.justplay.presentation.validator;

import it.univaq.disim.mwt.justplay.business.BusinessException;
import it.univaq.disim.mwt.justplay.business.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

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

	private boolean validateEmail(final String email) {
		Matcher matcher = PATTERN.matcher(email);
		return matcher.matches();
	}
}
