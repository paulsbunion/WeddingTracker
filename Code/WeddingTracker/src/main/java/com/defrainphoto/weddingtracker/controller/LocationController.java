package com.defrainphoto.weddingtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.model.Location;
import com.defrainphoto.weddingtracker.model.LocationManager;

@Controller
public class LocationController {

private LocationManager locationManager  = new LocationManager();
	
	@RequestMapping(value = "/createLocation", method = RequestMethod.GET)
	public ModelAndView createLocation() {
		
		ModelAndView model = new ModelAndView("createLocation", "command", new Location());
		
//		String[] yNChoice = {"N", "Y"};
//		
//		model.addObject("yNChoice", yNChoice);
		
		return model;
	}
	
	@RequestMapping(value = "/addLocation", method = RequestMethod.POST)
	public String addLocation(@ModelAttribute("Location")Location location, ModelMap model) {
		
		model.addAttribute("city", location.getCity());
		model.addAttribute("state", location.getState());
		model.addAttribute("zip", location.getZip());
		model.addAttribute("street", location.getStreet());
		model.addAttribute("description", location.getDescription());
		
		location.setLocationId("");
		location = locationManager.addLocation(location);
		
		model.addAttribute("locationId", location.getLocationId());
		return "addLocation";
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(EventType.class, new EventTypeEditor());
//		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
//	}
}
