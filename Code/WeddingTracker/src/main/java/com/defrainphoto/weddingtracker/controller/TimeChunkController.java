package com.defrainphoto.weddingtracker.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
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
import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.ClientManager;
import com.defrainphoto.weddingtracker.model.Location;
import com.defrainphoto.weddingtracker.model.LocationManager;
import com.defrainphoto.weddingtracker.model.Photographer;
import com.defrainphoto.weddingtracker.model.PhotographerManager;
import com.defrainphoto.weddingtracker.model.TimeChunk;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

@Controller
public class TimeChunkController {
	
	private TimelineManager timelineManager = new TimelineManager();
	private LocationManager locationManager = new LocationManager();
	private ClientManager clientManager = new ClientManager();
	private PhotographerManager photographerManager = new PhotographerManager();
	
	@RequestMapping(value ="/createTimeSlice/{eventId}", method = RequestMethod.GET)
	public ModelAndView createTimeChunk(@PathVariable("eventId") String eventId) {

		// get the timeline for reference
		Timeline timeline = new Timeline();
		timeline.setEventId(eventId);
		TimeChunk timeChunk = new TimeChunk();
		timeChunk.setTimeline(timeline);
		timeChunk.setEventId(eventId);
		
		ModelAndView model = new ModelAndView("createTimeSlice", "command", timeChunk);
		
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
		
//		System.out.println("before POST");
		return model;
	}
	
	@RequestMapping(value = "/addTimeSlice", method = RequestMethod.POST)
	public void addTimeChunk(@ModelAttribute("timeChunk") TimeChunk timeChunk, ModelMap model,
			@ModelAttribute("location") String location, 
			@ModelAttribute("client") Client client) {
		
		model.addAttribute("client", timeChunk.getClient());
		model.addAttribute("description", timeChunk.getDescription());
		model.addAttribute("duration", timeChunk.getDuration());
		model.addAttribute("location", timeChunk.getLocation());
		model.addAttribute("photographers", timeChunk.getPhotographers());
		model.addAttribute("position", timeChunk.getPosition());
		model.addAttribute("startTime", timeChunk.getStartTime());
		model.addAttribute("eventId", timeChunk.getEventId());
		
		timeChunk = timelineManager.timeChunkManager.addTimeChunk(timeChunk);
		
		model.addAttribute("chunkId", timeChunk.getChunkId());
	}
	
	@RequestMapping(value="/editTimeSlice/{eventId}/{chunkId}", method = RequestMethod.GET)
	public ModelAndView editTimeSlice(@PathVariable("eventId") String eventId, 
		 @PathVariable("chunkId") String chunkId) {
		
		Timeline timeline = new Timeline(eventId, null, null, null, null);
		timeline = timelineManager.getTimelineByEventId(timeline);
		
		TimeChunk timeChunk = new TimeChunk(chunkId, timeline, 0, null, null, null, "", null, null);
		timeChunk = timelineManager.timeChunkManager.getTimeChunkByIdAndTimeline(timeChunk);
		
		System.out.println("here");
		System.out.println(timeChunk);
		
		ModelAndView model = new ModelAndView("editTimeSlice", "command", timeChunk);
		
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

		return model;
	}
	
	@RequestMapping(value="/editTimeSlice", method = RequestMethod.POST)
	public String editTimeSliceSaved(@ModelAttribute("TimeChunk")TimeChunk timeChunk, ModelMap model) {
		model.addAttribute("chunkId", timeChunk.getChunkId());
		model.addAttribute("eventId", timeChunk.getEventId());
		model.addAttribute("position", timeChunk.getPosition());
		model.addAttribute("location", timeChunk.getLocation());
		model.addAttribute("description", timeChunk.getDescription());
		model.addAttribute("client", timeChunk.getClient());
		
		System.out.println("the chunk");
		System.out.println(timeChunk);
		return "editTimeSliceSaved";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
		binder.registerCustomEditor(Client.class, new ClientEditor());
//		binder.registerCustomEditor(Timeline.class, new TimelineEditor());
		binder.registerCustomEditor(Location.class, new LocationEditor());
		binder.registerCustomEditor(Set.class, new PhotographerEditor());
	}
}
