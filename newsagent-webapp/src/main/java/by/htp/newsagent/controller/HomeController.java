package by.htp.newsagent.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.htp.newsagent.controller.form.Location;
import by.htp.newsagent.controller.form.LocationWebModel;

@Controller
public class HomeController {
	private static final String PAGE_ABOUT_ALIAS = "about";
	
	@RequestMapping(value = {"/","/about","/home"}, method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();
		locationModel.setCurrentLocation(Location.ABOUT);
		locationModel.setPreviousLocation(Location.NEWS);
		
		model.addAttribute(LocationWebModel.ALIAS, locationModel);
		
		return PAGE_ABOUT_ALIAS;
	}
}
