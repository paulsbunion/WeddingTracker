package com.defrainphoto.weddingtracker.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.editors.ClientEditor;
import com.defrainphoto.weddingtracker.editors.LocationEditor;
import com.defrainphoto.weddingtracker.editors.PhotographerEditor;
import com.defrainphoto.weddingtracker.editors.SqlTimeEditor;
import com.defrainphoto.weddingtracker.editors.TimelineEditor;
import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.ClientManager;
import com.defrainphoto.weddingtracker.model.Event;
import com.defrainphoto.weddingtracker.model.EventManager;
import com.defrainphoto.weddingtracker.model.Location;
import com.defrainphoto.weddingtracker.model.LocationManager;
import com.defrainphoto.weddingtracker.model.Photographer;
import com.defrainphoto.weddingtracker.model.PhotographerManager;
import com.defrainphoto.weddingtracker.model.TimeChunk;
import com.defrainphoto.weddingtracker.model.TimeChunkManager;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

@Controller
public class TimeChunkController {
	
	@Autowired
	private EventManager eventManager;
//	private EventManager eventManager = new EventManager();
	@Autowired
	private TimelineManager timelineManager;
//	private TimelineManager timelineManager = new TimelineManager();
	@Autowired
	private LocationManager locationManager;
//	private LocationManager locationManager = new LocationManager();
	@Autowired
	private ClientManager clientManager;
//	private ClientManager clientManager = new ClientManager();
	@Autowired
	private PhotographerManager photographerManager;
//	private PhotographerManager photographerManager = new PhotographerManager();

	@RequestMapping(value ={"/createTimeSlice/{eventId}", "/WeddingTracker/createTimeSlice/{eventId}"}, method = RequestMethod.GET)
	public ModelAndView createTimeChunk(@PathVariable("eventId") String eventId) {

		// get the timeline for reference
		Timeline timeline = new Timeline();
		timeline.setEventId(eventId);
		TimeChunk timeChunk = new TimeChunk();
		timeChunk.setTimeline(timeline);
		timeChunk.setEventId(eventId);
		
		ModelAndView model = new ModelAndView("timeline/createTimeSlice", "command", timeChunk);
		
		// add the lists
		List<Location> locationList = locationManager.getAllLocations();
		if (locationList == null) {
			locationList = new ArrayList<>();
		}
		List<Client> clientList = clientManager.getAllClients();
		if (clientList == null) {
			clientList = new ArrayList<>();
		}
		List<Photographer> photographerList = photographerManager.getAllPhotographers();
		if (photographerList == null) {
			photographerList = new ArrayList<>();
		}
		
		model.addObject("locationList", locationList);
		model.addObject("clientList", clientList);
		model.addObject("photographerList", photographerList);
		model.addObject("timeline", timeline);
		model.addObject("hrMap", generateHrData());
		model.addObject("minMap", generateMinData());
		
//		System.out.println("before POST");
		return model;
	}
	
	@RequestMapping(value = {"/addTimeSlice", "/WeddingTracker/addTimeSlice"}, method = RequestMethod.POST)
	public String addTimeChunk(@ModelAttribute("timeChunk") TimeChunk timeChunk, ModelMap model,
			@ModelAttribute("location") String location, 
			@ModelAttribute("client") Client client) {
		System.out.println("hello, me");
		model.addAttribute("client", timeChunk.getClient());
		model.addAttribute("description", timeChunk.getDescription());
		model.addAttribute("durationHr", timeChunk.getDurationHr());
		model.addAttribute("durationMin", timeChunk.getDurationMin());
		model.addAttribute("location", timeChunk.getLocation());
		model.addAttribute("photographers", timeChunk.getPhotographers());
		model.addAttribute("position", timeChunk.getPosition());
		model.addAttribute("startTime", timeChunk.getStartTime());
		model.addAttribute("eventId", timeChunk.getEventId());
		
		timeChunk = timelineManager.timeChunkManager.addTimeChunk(timeChunk);
		
		model.addAttribute("chunkId", timeChunk.getChunkId());
		return "timeline/addTimeSlice";
	}
	
	@RequestMapping(value={"/editTimeSlice/{eventId}/{chunkId}", "/WeddingTracker/editTimeSlice/{eventId}/{chunkId}"}, method = RequestMethod.GET)
	public ModelAndView editTimeSlice(@PathVariable("eventId") String eventId, 
		 @PathVariable("chunkId") String chunkId) {
		
		// get the timeline
		Timeline timeline = new Timeline(eventId, null, null, null, null);
		timeline = timelineManager.getTimelineByEventId(timeline);
		
		System.out.println("not bad");
		System.out.println(timeline);
		
		TimeChunk timeChunk = new TimeChunk(chunkId, timeline, 0, null, null, null, null, "", null, null);
		timeChunk = timelineManager.timeChunkManager.getTimeChunkByIdAndTimeline(timeChunk);
		
		timeChunk.setEventId(eventId);
		
		System.out.println("here");
		System.out.println(timeChunk);
		System.out.println(timeChunk.getTimeline());
		
		ModelAndView model = new ModelAndView("timeline/editTimeSlice", "command", timeChunk);
		
		// add the lists
		List<Location> locationList = locationManager.getAllLocations();
		if (locationList == null) {
			locationList = new ArrayList<>();
		}
		List<Client> clientList = clientManager.getAllClients();
		if (clientList == null) {
			clientList = new ArrayList<>();
		}
		List<Photographer> photographerList = photographerManager.getAllPhotographers();
		if (photographerList == null) {
			photographerList = new ArrayList<>();
		}
		
		model.addObject("locationList", locationList);
		model.addObject("clientList", clientList);
		model.addObject("photographerList", photographerList);
		
		model.addObject("timeline", timeline);
		model.addObject("timelineStart", timeline.getStartTime());
		model.addObject("timelineTotal", timeline.getTotalTime());
		model.addObject("chunkId", chunkId);
		model.addObject("eventId", eventId);
		model.addObject("durationHr", timeChunk.getDurationHr());
		model.addObject("durationMin", timeChunk.getDurationMin());
		model.addObject("timeChunk", timeChunk);
		model.addObject("hrMap", generateHrData());
		model.addObject("minMap", generateMinData());
		
		System.out.println("Data");
		System.out.println(timeline);
		System.out.println(chunkId);
		System.out.println(eventId);

		return model;
	}
	
	@RequestMapping(value={"/editTimeSlice", "/WeddingTracker/editTimeSlice"}, method = RequestMethod.POST)
	public String editTimeSliceSaved(@ModelAttribute("timeChunk")TimeChunk timeChunk,
			@ModelAttribute("timeline")Timeline timeline,
			@ModelAttribute("eventId")String eventId, ModelMap model) {
		
		model.addAttribute("chunkId", timeChunk.getChunkId());
		model.addAttribute("eventId", timeChunk.getEventId());
		model.addAttribute("position", timeChunk.getPosition());
		model.addAttribute("location", timeChunk.getLocation());
		model.addAttribute("description", timeChunk.getDescription());
		model.addAttribute("client", timeChunk.getClient());
		model.addAttribute("photographers", timeChunk.getPhotographers());
		model.addAttribute("startTime", timeChunk.getStartTime());
		
		System.out.println(timeChunk.getPhotographers());
		System.out.println("before error");
		timeline.setEventId(eventId);
		System.out.println(eventId);
//		timeline.setStartTime(start);
//		timeline.setTotalTime(total);
		
		timeChunk.setTimeline(timeline);
		System.out.println(timeChunk);
		System.out.println(timeChunk.getTimeline());
		System.out.println(timeChunk.getTimeline().getEventId());
		System.out.println(timeline);
		timelineManager.timeChunkManager.updateTimeChunk(timeChunk);
		System.out.println("after error");
		
		System.out.println("the chunk");
		System.out.println(timeChunk);
		return "timeline/editTimeSliceSaved";
	}
	
	@RequestMapping(value={"/deleteTimeSlice/{eventId}/{chunkId}", "/WeddingTracker/deleteTimeSlice/{eventId}/{chunkId}"})
	public String deleteTimeSlice(@PathVariable("eventId") String eventId, 
		 @PathVariable("chunkId") String chunkId, Model model) {
		
		// get the timeline
		Timeline timeline = new Timeline(eventId, null, null, null, null);
		timeline = timelineManager.getTimelineByEventId(timeline);
		
		Event event = new Event(eventId, "", null, null, null, null, null, "", "", null, null);
		event = eventManager.getEventById(event);
				
		TimeChunk timeChunk = new TimeChunk(chunkId, timeline, 0, null, null, null, null, "", null, null);
		timeChunk = timelineManager.timeChunkManager.getTimeChunkByIdAndTimeline(timeChunk);
		
		timeChunk.setEventId(eventId);
		
		timelineManager.timeChunkManager.DeleteTimeChunkById(timeChunk);
		
		model.addAttribute("timeline", timeline);
		model.addAttribute("eventId", eventId);
		
		model.addAttribute("timeSlices", timelineManager.getTimeChunks(timeline));
		model.addAttribute("eventName", event.getEventName());

		return "timeline/listTimeSlices";
	}
	
	protected List<String> generateHrData() {
		List<String> hourChoice = new ArrayList<String>();
		for (int i = 0; i < 25; i++) {
			hourChoice.add("" + i);
		}
		
		return hourChoice;
	}
	
	protected List<String> generateMinData() {
		List<String> minChoice = new ArrayList<String>();
		for (int i = 0; i < 61; i+= 5) {
			minChoice.add("" + i);
		}
		
		return minChoice;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
		binder.registerCustomEditor(Client.class, new ClientEditor());
		binder.registerCustomEditor(Timeline.class, new TimelineEditor());
		binder.registerCustomEditor(Location.class, new LocationEditor());
		binder.registerCustomEditor(Set.class, new PhotographerEditor());
	}
}
