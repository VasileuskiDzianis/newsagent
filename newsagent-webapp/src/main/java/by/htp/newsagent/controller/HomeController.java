package by.htp.newsagent.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.htp.newsagent.model.Location;
import by.htp.newsagent.model.LocationWebModel;

@Controller
public class HomeController {
	
	@RequestMapping(value = {"/","/about","/home"}, method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		LocationWebModel locationModel = new LocationWebModel();
		locationModel.setCurrentLocation(Location.ABOUT);
		locationModel.setPreviousLocation(Location.NEWS);
		
		model.addAttribute("locationModel", locationModel);
		
		return "about";
	}
}
