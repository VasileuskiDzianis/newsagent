package by.htp.newsagent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.htp.newsagent.model.Location;
import by.htp.newsagent.model.LocationModel;

@Controller
public class HomeController {
	private LocationModel locationModel = new LocationModel();
	
	@RequestMapping(value = {"/","/about"}, method = RequestMethod.GET)
	public String home(Model model) {
		locationModel.setCurrentLocation(Location.ABOUT);
		locationModel.setPreviousLocation(Location.NEWS);
		
		model.addAttribute("locationModel", locationModel);
		
		return "about";
	}
}
