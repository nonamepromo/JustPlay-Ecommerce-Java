package it.univaq.disim.mwt.justplay.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import it.univaq.disim.mwt.justplay.configuration.JustPlayProperties;

@ControllerAdvice
public class GlobalBindingInitializer {

	@Autowired
	private JustPlayProperties properties;

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.addCustomFormatter(new DateFormatter(properties.getDateFormat()));
	}
}