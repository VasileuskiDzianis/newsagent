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
import by.htp.newsagent.model.LocationModel;



@ControllerAdvice
public class ExceptionHandlerController {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);
	private LocationModel locationModel = new LocationModel();
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler
	public String handle(Exception e, Model model, Locale locale) {
		LOGGER.error("Exception occurred, ", e);
		e.printStackTrace();
		
		locationModel.setCurrentLocation(Location.ERROR);
		locationModel.setPreviousLocation(Location.NEWS);
		
		model.addAttribute("locationModel", locationModel);
		model.addAttribute("errorMessage", messageSource.getMessage("message.internalError", null, locale));

		return "error";
	}
}