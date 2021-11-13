package it.univaq.disim.mwt.justplay.presentation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest request, Exception ex, Model model,
			RedirectAttributes redirAttrs) {
		redirAttrs.addFlashAttribute("error", (ex.getCause() == null) ? ex : ex.getCause().getMessage());
		return "redirect:" + request.getServletPath();
	}

}
