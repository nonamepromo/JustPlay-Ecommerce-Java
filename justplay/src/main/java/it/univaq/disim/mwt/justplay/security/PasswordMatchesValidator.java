package it.univaq.disim.mwt.justplay.security;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import it.univaq.disim.mwt.justplay.domain.Utente;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final Utente user = (Utente) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}