package com.defrainphoto.weddingtracker.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import com.defrainphoto.weddingtracker.editors.EventTypeEditor;
import com.defrainphoto.weddingtracker.editors.SqlDateEditor;
import com.defrainphoto.weddingtracker.editors.SqlTimeEditor;
import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.Event;
import com.defrainphoto.weddingtracker.model.EventManager;
import com.defrainphoto.weddingtracker.model.EventType;
import com.defrainphoto.weddingtracker.model.EventTypeManager;
import com.defrainphoto.weddingtracker.model.Mileage;
import com.defrainphoto.weddingtracker.model.MileageManager;
import com.defrainphoto.weddingtracker.model.TimeChunk;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

@Controller
public class EventController {
	@Autowired
	private EventManager eventManager;
//	private EventManager eventManager = new EventManager();
	@Autowired
	private EventTypeManager eventTypeManager;
//	private EventTypeManager eventTypeManager = new EventTypeManager();
	@Autowired
	private TimelineManager timelineManager;
//	private TimelineManager timelineManager = new TimelineManager();
	@Autowired
	private MileageManager mileageManager;
	
	
	@RequestMapping(value = {"/createEvent", "/WeddingTracker/createEvent"}, method = RequestMethod.GET)
	public ModelAndView createEvent() {
		
		ModelAndView model = new ModelAndView("event/createEvent", "event", new Event());
		
		List<EventType> eventTypeList = eventTypeManager.getAllEventTypes();
		
		model.addObject("eventTypeList", eventTypeList);
		
		return model;
		
//		Map<String, Object> models = new HashMap<String, Object>();
//		models.put("event", new Event());
//		models.put("eventType", new EventType());
		
//		return new ModelAndView("createEvent", "command", models);
	}
	
	@RequestMapping(value = {"/addEvent", "/WeddingTracker/addEvent"})
	public String addEvent(@Valid @ModelAttribute("event")Event event, BindingResult result,
			@RequestParam(value = "submitCancelParam") String submitCancel, ModelMap model) {
		
		String returnValue = "event/addEvent";
		
		System.out.println("adding an event");
		System.out.println(event);
		
		// if cancel, go to list events page
		if (submitCancel.equalsIgnoreCase("cancel")) {
			returnValue = "redirect:/listEvents";
		}
		else if (result.hasErrors()) {
			// 
			model.addAttribute("eventTypeList", eventTypeManager.getAllEventTypes());
			returnValue = "event/createEvent";
		}
		else {
			model.addAttribute("eventName", 	event.getEventName());
			model.addAttribute("eventType",		event.getEventType());
			model.addAttribute("eventDate", 	event.getEventDate());
			model.addAttribute("startTime",		event.getStartTime());
			model.addAttribute("duration",		event.getDuration());
			model.addAttribute("extraCost",		event.getExtraCost());
			model.addAttribute("notes", 		event.getNotes());
			
			event.setEventId("");
			try {
				event = eventManager.addEvent(event);
			}
			catch (EntityExistsException e) {
				
			}
			
			model.addAttribute("eventId", event.getEventId());
		}
		System.out.println("the value");
		System.out.println(submitCancel);
		System.out.println(returnValue);
			return returnValue;
	}
	
	@RequestMapping(value ={"/editEvent/{eventId}", "/WeddingTracker/editEvent/{eventId}"}, method=RequestMethod.GET)
	public ModelAndView editEvent(@PathVariable("eventId") String eventId) {
		Event event = new Event(eventId, "", null, null, null, null, null, "", "", null, null);
		event = eventManager.getEventById(event);
		
		ModelAndView model = new ModelAndView("event/editEvent", "event", event);
		
		List<EventType> eventTypeList = eventTypeManager.getAllEventTypes();
		System.out.println("the list of event Types");
		System.out.println(eventTypeList);
		model.addObject("eventTypeList", eventTypeList);
		
		model.addObject("eventId", eventId);
		model.addObject("duration", event.getDuration());
		
//		model.addObject("eventType", event.getEventType());
		
		return model;
	}
	
	@RequestMapping(value={"/editEvent", "/WeddingTracker/editEvent"}, method = RequestMethod.POST)
	public String editEventSaved(@Valid @ModelAttribute("event")Event event, BindingResult result,
			@RequestParam(value = "submitCancelParam") String submitCancel,			
//			@ModelAttribute("eventType")EventType eventType,
			@ModelAttribute("duration")Time duration, ModelMap model) {
		System.out.println("begin edit");
		System.out.println(event);
		String returnValue = "event/editEventSaved";
		
		// if cancel, go to list events page
		if (submitCancel.equalsIgnoreCase("cancel")) {
			returnValue = "redirect:/listEvents";
		}
		else if (result.hasErrors()) {
			// 
			model.addAttribute("eventTypeList", eventTypeManager.getAllEventTypes());
			returnValue = "event/editEvent";
		}
		else {		
			model.addAttribute("eventName", event.getEventName());
	//		model.addAttribute("eventType", event.getEventType());
			model.addAttribute("eventType", event.getEventType());
			model.addAttribute("eventDate", event.getEventDate());
			model.addAttribute("startTime", event.getStartTime());
			model.addAttribute("notes", event.getNotes());
			model.addAttribute("duration", duration);
			try {
				System.out.println("updating the event");
				System.out.println(event);
				eventManager.updateEvent(event);
			}
			catch (EntityExistsException e) {
				
			}
			
			model.addAttribute("eventId", event.getEventId());
		}
		
		return returnValue;
	}
	
	@RequestMapping(value={"/listEvents", "/WeddingTracker/listEvents"})
	public String listEvents(Map<String, Object> map) {
		Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		List<Event> events = eventManager.getEventsByToday(today, false);
		
		map.put("eventList", events);
//		map.put("eventList", eventManager.getAllEvents());
		// get timeline id's
		map.put("timelineIdMap", timelineManager.getallTimelineIds());
//		map.put("eventMileage", generateEventMileageList(events));
		
		map.put("pastFuture", "future");
		
		return "event/listEvents";
	}
	
//	private List<Mileage> generateEventMileageList(List<Event> events) {
//		List<Mileage> mileageList = mileageManager.getAllEventsMileage();
//		List<Mileage> result = new ArrayList<Mileage>();
//		String eventId = "";
//		
//		for (Event e : events) {
//			eventId = e.getEventId();
//			for
//			result.add(mileageList.)
//		}
//		return null;
//	}

	@RequestMapping(value={"/listPastEvents", "/WeddingTracker/listPastEvents"})
	public String listPastEvents(Map<String, Object> map) {
		Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		map.put("eventList", eventManager.getEventsByToday(today, true));
//		map.put("eventList", eventManager.getAllEvents());
		// get timeline id's
		map.put("timelineIdMap", timelineManager.getallTimelineIds());
		
		map.put("pastFuture", "past");
		
		return "event/listEvents";
	}
	
	@RequestMapping(value={"/deleteEvent/{eventId}", "/WeddingTracker/deleteEvent/{eventId}"})
	public String deleteTimeSlice(@PathVariable("eventId") String eventId, Model model, Map<String, Object> map) {
		
		// get the event
		System.out.println("in it");
		Event event = new Event(eventId, "", null, null, null, null, null, "", "", null, null);
		event = eventManager.getEventById(event);
		System.out.println("deleting");
		
		
		eventManager.deleteEventById(event);
		Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		map.put("eventList", eventManager.getEventsByToday(today, false));
		// get timeline id's
		map.put("timelineIdMap", timelineManager.getallTimelineIds());
		
		map.put("pastFuture", "future");
		
		return "event/listEvents";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(EventType.class, new EventTypeEditor(eventTypeManager));
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
		binder.registerCustomEditor(Date.class, new SqlDateEditor());
	}
}
