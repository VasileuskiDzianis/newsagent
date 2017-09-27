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

import by.htp.newsagent.controller.form.Location;
import by.htp.newsagent.controller.form.LocationWebModel;
import by.htp.newsagent.service.exception.NewsValidationException;

@ControllerAdvice
public class ExceptionHandlerController {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);
	
	private static final String PAGE_ERROR_ALIAS = "error";
	private static final String MODEL_ERR_MESSAGE_ALIAS = "errorMessage";

	private static final String I18N_INTERNAL_ERROR = "messages.message.internalError";
	private static final String I18N_ILLEGAL_ARGUMENT = "messages.message.illegalNewsArgument";
	private static final String I18N_404_NOT_FOUND = "messages.message.NotFound404";
	
	

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleGlobalExceptions(Exception e, Model model, Locale locale) {
		LOGGER.error("Exception occurred, ", e);
		
		LocationWebModel locationModel = new LocationWebModel();
		locationModel.setCurrentLocation(Location.ERROR);
		locationModel.setPreviousLocation(Location.NEWS);
		model.addAttribute(LocationWebModel.ALIAS, locationModel);
		model.addAttribute(MODEL_ERR_MESSAGE_ALIAS, messageSource.getMessage(I18N_INTERNAL_ERROR, null, locale));

		return PAGE_ERROR_ALIAS;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleValidationExceptions(NewsValidationException e, Model model, Locale locale) {
		LOGGER.error("NewsValidation Exception occurred, ", e);
		
		LocationWebModel locationModel = new LocationWebModel();
		locationModel.setCurrentLocation(Location.ERROR);
		locationModel.setPreviousLocation(Location.NEWS);
		model.addAttribute(LocationWebModel.ALIAS, locationModel);
		model.addAttribute(MODEL_ERR_MESSAGE_ALIAS, messageSource.getMessage(I18N_ILLEGAL_ARGUMENT, null, locale));

		return PAGE_ERROR_ALIAS;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException(Model model, Locale locale) {
		LocationWebModel locationModel = new LocationWebModel();
		locationModel.setCurrentLocation(Location.ERROR);
		locationModel.setPreviousLocation(Location.NEWS);
		model.addAttribute(LocationWebModel.ALIAS, locationModel);
		model.addAttribute(MODEL_ERR_MESSAGE_ALIAS, messageSource.getMessage(I18N_404_NOT_FOUND, null, locale));

		return PAGE_ERROR_ALIAS;
	}
}