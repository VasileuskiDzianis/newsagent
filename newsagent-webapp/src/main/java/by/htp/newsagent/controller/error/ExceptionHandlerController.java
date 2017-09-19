package by.htp.newsagent.controller.error;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import by.htp.newsagent.model.Location;
import by.htp.newsagent.model.LocationWebModel;
import by.htp.newsagent.service.exception.NewsValidationException;



@ControllerAdvice
public class ExceptionHandlerController {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler
	public String handleGlobalExceptions(Exception e, Model model, Locale locale) {
		LocationWebModel locationModel = new LocationWebModel();
		
		LOGGER.error("Exception occurred, ", e);
		e.printStackTrace();
		
		locationModel.setCurrentLocation(Location.ERROR);
		locationModel.setPreviousLocation(Location.NEWS);
		
		model.addAttribute("locationModel", locationModel);
		model.addAttribute("errorMessage", messageSource.getMessage("message.internalError", null, locale));

		return "error";
	}
	
	@ExceptionHandler
	public String handleValidationExceptions(NewsValidationException e, Model model, Locale locale) {
		LocationWebModel locationModel = new LocationWebModel();
		
		LOGGER.error("NewsValidation Exception occurred, ", e);
		e.printStackTrace();
		
		locationModel.setCurrentLocation(Location.ERROR);
		locationModel.setPreviousLocation(Location.NEWS);
		
		model.addAttribute("locationModel", locationModel);
		model.addAttribute("errorMessage", messageSource.getMessage("message.illegalNewsArgument", null, locale));
		
		return "error";
	}
}