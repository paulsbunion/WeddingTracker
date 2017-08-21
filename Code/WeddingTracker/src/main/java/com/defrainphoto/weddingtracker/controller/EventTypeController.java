package com.defrainphoto.weddingtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.model.EventType;
import com.defrainphoto.weddingtracker.model.EventTypeManager;

@Controller
public class EventTypeController {
	
	private EventTypeManager eventTypeManager = new EventTypeManager();
	
	@RequestMapping(value = "/createEventType", method = RequestMethod.GET)
	public ModelAndView createEvent() {
		
		return new ModelAndView("createEventType", "command", new EventType());
	}
	
	@RequestMapping(value = "/addEventType", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute("WeddingTracker")EventType eventType, ModelMap model) {
		
		model.addAttribute("eventType", eventType.getEventType());
		model.addAttribute("baseCost", eventType.getBaseCost());
		
		eventType.setEventTypeId("");
		System.out.println();
		eventTypeManager.addEventType(eventType);
		
		return "addEventType";
	}
}
