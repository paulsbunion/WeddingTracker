package com.defrainphoto.weddingtracker.controller;

import java.sql.Time;
import java.util.List;

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
		List<Client> clientList = clientManager.getAllClients();
		List<Photographer> photographerList = photographerManager.getAllPhotographers();
		
		model.addObject("locationList", locationList);
		model.addObject("clientList", clientList);
		model.addObject("PhotographerList", photographerList);
		model.addObject("timeline", timeline);
		
		System.out.println("before POST");
		return model;
	}
	
	@RequestMapping(value = "/addTimeSlice", method = RequestMethod.POST)
	public void addTimeChunk(@ModelAttribute("timeChunk") TimeChunk timeChunk, ModelMap model
			) {
		
		System.out.println("the model");
		System.out.println(model);
		model.addAttribute("client", timeChunk.getClient());
		model.addAttribute("description", timeChunk.getDescription());
		model.addAttribute("duration", timeChunk.getDuration());
		model.addAttribute("location", timeChunk.getLocation());
		model.addAttribute("photographers", timeChunk.getPhotographers());
		model.addAttribute("position", timeChunk.getPosition());
		model.addAttribute("startTime", timeChunk.getStartTime());
		model.addAttribute("eventId", timeChunk.getEventId());
//		model.addAttribute("timeline", timeChunk.getTimeline());
		
		System.out.println("the timechunk timeline event id");
//		System.out.println(model.get(timeChunk));
		System.out.println(model.get("eventId"));
		System.out.println(model.get("timeline"));
		timeChunk = timelineManager.timeChunkManager.addTimeChunk(timeChunk);
		System.out.println("the added chunk");
		System.out.println(timeChunk);
		
		model.addAttribute("chunkId", timeChunk.getChunkId());
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
//		binder.registerCustomEditor(Timeline.class, new TimelineEditor());
	}
}
