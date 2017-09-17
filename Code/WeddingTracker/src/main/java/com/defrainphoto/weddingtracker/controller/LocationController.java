package com.defrainphoto.weddingtracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.editors.LocationEditor;
import com.defrainphoto.weddingtracker.model.Location;
import com.defrainphoto.weddingtracker.model.LocationManager;

@Controller
public class LocationController {

	@Autowired
	private LocationManager locationManager;
//	private LocationManager locationManager  = new LocationManager();
	
	@RequestMapping(value = {"/createLocation", "/WeddingTracker/createLocation"}, method = RequestMethod.GET)
	public ModelAndView createLocation() {
		
		ModelAndView model = new ModelAndView("location/createLocation", "location", new Location());
		model.addObject("stateMap", generateStateData());
		model.addObject("state", "OH");
//		String[] yNChoice = {"N", "Y"};
//		
//		model.addObject("yNChoice", yNChoice);
		
		return model;
	}
	
	@RequestMapping(value = {"/addLocation", "/WeddingTracker/addLocation"}, method = RequestMethod.POST)
	public String addLocation(@Valid @ModelAttribute("location") Location location, BindingResult result,
			@RequestParam(value = "submitCancelParam") String submitCancel, ModelMap model) {
		
		String returnValue = "location/addLocation";
		
		System.out.println("in location controller");
		// if cancel, go to list events page
		if (submitCancel.equalsIgnoreCase("cancel")) {
			returnValue = "redirect:/listLocations";
		}
		else if (result.hasErrors()) {
			System.out.println("there are errors!");
			returnValue = "location/createLocation";
		}
		else {
			model.addAttribute("city", location.getCity());
			model.addAttribute("state", location.getState());
			model.addAttribute("zip", location.getZip());
			model.addAttribute("street", location.getStreet());
			model.addAttribute("description", location.getDescription());
			
			location.setLocationId("");
			try {
				location = locationManager.addLocation(location);
			}
			catch (EntityExistsException e) {
				
			}
			
			model.addAttribute("locationId", location.getLocationId());
		}
		
		return returnValue;
	}
	
	@RequestMapping(value={"/listLocations", "/WeddingTracker/listLocations"})
	public String listPhotographers(Map<String, Object> map) {
		
		map.put("locationList", locationManager.getAllLocations());
		
		return "location/listLocations";
	}
	
	@RequestMapping(value ={"/editLocation/{locationId}", "/WeddingTracker/editLocation/{locationId}"}, method=RequestMethod.GET)
	public ModelAndView editLocation(@PathVariable("locationId") String locationId) {
		
		Location location = new Location(locationId, "", "", "", "", "");
		location = locationManager.getLocationById(location);
		System.out.println("the location 1");
		System.out.println(location);
		ModelAndView model = new ModelAndView("location/editLocation", "location", location);
		model.addObject("locationId", locationId);
		model.addObject("stateMap", generateStateData());
		
		return model;
	}
	
	@RequestMapping(value={"/editLocation", "/WeddingTracker/editLocation"}, method = RequestMethod.POST)
	public String editLocationSaved(@Valid @ModelAttribute("location")Location location, BindingResult result,
			@RequestParam(value = "submitCancelParam") String submitCancel, ModelMap model) {
		
		String returnValue = "location/editLocationSaved";
		
		// if cancel, go to list events page
		if (submitCancel.equalsIgnoreCase("cancel")) {
			returnValue = "redirect:/listLocations";
		}
		else if (result.hasErrors()) {
			// add state map in
			model.addAttribute("stateMap", generateStateData());
			returnValue = "location/editLocation";
		}
		else {
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
		}
		
		return returnValue;
	}
	
	protected List<String> generateStateData() {
		List<String> stateChoice = new ArrayList<String>();
		String[] states = {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA",
				"HI","ID","IL","IN","IA","KS","KY","LA","ME","MD",
				"MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ",
				"NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
				"SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};

		for (int i = 0; i < 50; i++) {
			stateChoice.add(states[i]);
		}
		
		return stateChoice;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Location.class, new LocationEditor());
	}
}
