package com.defrainphoto.weddingtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.model.Photographer;
import com.defrainphoto.weddingtracker.model.PhotographerManager;

@Controller
public class PhotographerController {
private PhotographerManager photographerManager  = new PhotographerManager();
	
	@RequestMapping(value = "/createPhotographer", method = RequestMethod.GET)
	public ModelAndView createPhotographer() {
		
		ModelAndView model = new ModelAndView("createPhotographer", "command", new Photographer());
		
//		String[] yNChoice = {"N", "Y"};
//		
//		model.addObject("yNChoice", yNChoice);
		
		return model;
	}
	
	@RequestMapping(value = "/addPhotographer", method = RequestMethod.POST)
	public String addPhotographer(@ModelAttribute("Photograpehr")Photographer photographer, ModelMap model) {
		
		model.addAttribute("firstName", photographer.getFirstName());
		model.addAttribute("lastName",	photographer.getLastName());
		model.addAttribute("events",	photographer.getEvents());
		
		photographer.setStaffId("");
		photographer.setEvents(null);
		photographer = photographerManager.addPhotographer(photographer);
		
		model.addAttribute("staffId", photographer.getStaffId());
		return "addPhotographer";
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(EventType.class, new EventTypeEditor());
//		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
//	}
}
