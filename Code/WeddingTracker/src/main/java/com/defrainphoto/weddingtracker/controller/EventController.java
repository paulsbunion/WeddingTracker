package com.defrainphoto.weddingtracker.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.editors.EventTypeEditor;
import com.defrainphoto.weddingtracker.editors.SqlTimeEditor;
import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.Event;
import com.defrainphoto.weddingtracker.model.EventManager;
import com.defrainphoto.weddingtracker.model.EventType;
import com.defrainphoto.weddingtracker.model.EventTypeManager;
import com.defrainphoto.weddingtracker.model.TimeChunk;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

@Controller
public class EventController {
	private EventManager eventManager = new EventManager();
	private EventTypeManager eventTypeManager = new EventTypeManager();
	private TimelineManager timelineManager = new TimelineManager();
	
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
	
	@RequestMapping(value = "/addEvent")
	public String addEvent(@ModelAttribute("WeddingTracker")Event event, ModelMap model) {
		
		model.addAttribute("eventName", 	event.getEventName());
		model.addAttribute("eventType",		event.getEventType());
		model.addAttribute("eventDate", 	event.getEventDate());
		model.addAttribute("startTime",		event.getStartTime());
		model.addAttribute("duration",		event.getDuration());
		model.addAttribute("extraCost",		event.getExtraCost());
		model.addAttribute("notes", 		event.getNotes());
		
		event.setEventId("");
		event = eventManager.addEvent(event);
		
		model.addAttribute("eventId", event.getEventId());
		return "addEvent";
	}
	
	@RequestMapping(value ="/editEvent/{eventId}", method=RequestMethod.GET)
	public ModelAndView editEvent(@PathVariable("eventId") String eventId) {
		Event event = new Event(eventId, "", null, null, null, null, null, "", "", null, null);
		event = eventManager.getEventById(event);
		
		ModelAndView model = new ModelAndView("editEvent", "command", event);
		
		List<EventType> eventTypeList = eventTypeManager.getAllEventTypes();
		System.out.println("the list of event Types");
		System.out.println(eventTypeList);
		model.addObject("eventTypeList", eventTypeList);
		
		model.addObject("eventId", eventId);
		model.addObject("duration", event.getDuration());
		
		model.addObject("eventType", event.getEventType());
		
		return model;
	}
	
	@RequestMapping(value="/editEvent", method = RequestMethod.POST)
	public String editEventSaved(@ModelAttribute("event")Event event,
			@ModelAttribute("eventType")EventType eventType,
			@ModelAttribute("duration")Time duration, ModelMap model) {
		
		model.addAttribute("eventName", event.getEventName());
//		model.addAttribute("eventType", event.getEventType());
		model.addAttribute("eventType", eventType);
		model.addAttribute("eventDate", event.getEventDate());
		model.addAttribute("startTime", event.getStartTime());
		model.addAttribute("notes", event.getNotes());
		model.addAttribute("duration", duration);
		
		eventManager.updateEvent(event);
		
		model.addAttribute("eventId", event.getEventId());
		
		return "editEventSaved";
	}
	
	@RequestMapping(value="/listEvents")
	public String listEvents(Map<String, Object> map) {
		
		map.put("eventList", eventManager.getAllEvents());
		// get timeline id's
		map.put("timelineIdMap", timelineManager.getallTimelineIds());
		
		return "listEvents";
	}
	
	@RequestMapping(value="/deleteEvent/{eventId}")
	public String deleteTimeSlice(@PathVariable("eventId") String eventId, Model model, Map<String, Object> map) {
		
		// get the event
		System.out.println("in it");
		Event event = new Event(eventId, "", null, null, null, null, null, "", "", null, null);
		event = eventManager.getEventById(event);
		System.out.println("deleting");
		eventManager.deleteEventById(event);
		
		map.put("eventList", eventManager.getAllEvents());
		// get timeline id's
		map.put("timelineIdMap", timelineManager.getallTimelineIds());
		
		return "listEvents";
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(EventType.class, new EventTypeEditor());
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
	}
}
