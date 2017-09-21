package by.htp.newsagent.controller.error;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.HttpStatus;

import by.htp.newsagent.model.Location;
import by.htp.newsagent.model.LocationWebModel;
import by.htp.newsagent.service.exception.NewsValidationException;

@ControllerAdvice
public class ExceptionHandlerController {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
	@ResponseStatus(HttpStatus.BAD_REQUEST)
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

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException(Model model, Locale locale) {
		LocationWebModel locationModel = new LocationWebModel();

		locationModel.setCurrentLocation(Location.ERROR);
		locationModel.setPreviousLocation(Location.NEWS);

		model.addAttribute("locationModel", locationModel);
		model.addAttribute("errorMessage", messageSource.getMessage("message.NotFound404", null, locale));

		return "error";
	}
}