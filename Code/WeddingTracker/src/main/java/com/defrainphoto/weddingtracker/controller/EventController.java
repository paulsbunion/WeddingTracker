package com.defrainphoto.weddingtracker.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.editors.EventTypeEditor;
import com.defrainphoto.weddingtracker.editors.SqlTimeEditor;
import com.defrainphoto.weddingtracker.model.Event;
import com.defrainphoto.weddingtracker.model.EventManager;
import com.defrainphoto.weddingtracker.model.EventType;
import com.defrainphoto.weddingtracker.model.EventTypeManager;

@Controller
public class EventController {
	private EventManager eventManager = new EventManager();
	private EventTypeManager eventTypeManager = new EventTypeManager();
	
	@RequestMapping(value = "/createEvent", method = RequestMethod.GET)
	public ModelAndView createEvent() {
		
		ModelAndView model = new ModelAndView("createEvent", "command", new Event());
		
		List<EventType> eventTypeList = eventTypeManager.getAllEventTypes();
		
		model.addObject("eventTypeList", eventTypeList);
		
		return model;
		
//		Map<String, Object> models = new HashMap<String, Object>();
//		models.put("event", new Event());
//		models.put("eventType", new EventType());
		
//		return new ModelAndView("createEvent", "command", models);
	}
	
	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute("WeddingTracker")Event event, ModelMap model) {
		
		model.addAttribute("eventName", 	event.getEventName());
		model.addAttribute("eventType",		event.getEventType());
		model.addAttribute("eventDate", 	event.getEventDate());
		model.addAttribute("startTime",		event.getStartTime());
		model.addAttribute("duration",		event.getDuration());
		model.addAttribute("extraCost",		event.getExtraCost());
		model.addAttribute("notes", 		event.getNotes());
		
		event.setEventId("");
		event.setMultiClient("");
		event.setMultiStaff("");
		event = eventManager.addEvent(event);
		
		model.addAttribute("eventId", event.getEventId());
		return "addEvent";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(EventType.class, new EventTypeEditor());
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
	}
}
