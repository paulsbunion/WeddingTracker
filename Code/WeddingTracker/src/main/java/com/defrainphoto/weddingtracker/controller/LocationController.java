package com.defrainphoto.weddingtracker.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.editors.LocationEditor;
import com.defrainphoto.weddingtracker.model.Client;
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
	
	@RequestMapping(value="/listLocations")
	public String listPhotographers(Map<String, Object> map) {
		
		map.put("locationList", locationManager.getAllLocations());
		
		return "listLocations";
	}
	
	@RequestMapping(value ="/editLocation/{locationId}", method=RequestMethod.GET)
	public ModelAndView editLocation(@PathVariable("locationId") String locationId) {
		
		Location location = new Location(locationId, "", "", "", "", "");
		location = locationManager.getLocationById(location);
		System.out.println("the location 1");
		System.out.println(location);
		ModelAndView model = new ModelAndView("editLocation", "command", location);
		model.addObject("locationId", locationId);
		
		return model;
	}
	
	@RequestMapping(value="/editLocation", method = RequestMethod.POST)
	public String editLocationSaved(@ModelAttribute("location")Location location, ModelMap model) {
		
		model.addAttribute("street", location.getStreet());
		model.addAttribute("city", location.getCity());
		model.addAttribute("state", location.getState());
		model.addAttribute("zip", location.getZip());
		model.addAttribute("description", location.getDescription());
		
		System.out.println("the location 2");
		System.out.println(location);
		System.out.println(location.getLocationId());
		
		locationManager.updateLocation(location);
		
		model.addAttribute("locatinId", location.getLocationId());
		
		return "editLocationSaved";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Location.class, new LocationEditor());
	}
}
