package com.defrainphoto.weddingtracker.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.model.Client;
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
	
	@RequestMapping(value="/listEventTypes") 
	public String listEventTypes(Map<String, Object> map) {
		
		map.put("eventTypeList", eventTypeManager.getAllEventTypes());
		
		return "listEventTypes";
	}
	
	@RequestMapping(value ="/editEventType/{eventTypeId}", method=RequestMethod.GET)
	public ModelAndView editClient(@PathVariable("eventTypeId") String eventTypeId) {
		
		EventType eventType = new EventType(eventTypeId, "", "");
		eventType = eventTypeManager.getEventTypeById(eventType);
		
		ModelAndView model = new ModelAndView("editEventType", "command", eventType);
		model.addObject("eventTypeId", eventTypeId);
		
		return model;
	}
	
	@RequestMapping(value="/editEventType", method = RequestMethod.POST)
	public String editEventTypeSaved(@ModelAttribute("eventType")EventType eventType, ModelMap model) {
		
		model.addAttribute("eventTypeDesc", eventType.getEventType());
		model.addAttribute("baseCost", eventType.getBaseCost());

		eventTypeManager.updateEventType(eventType);
		
		model.addAttribute("eventTypeId", eventType.getEventTypeId());
		
		return "editEventTypeSaved";
	}
}
