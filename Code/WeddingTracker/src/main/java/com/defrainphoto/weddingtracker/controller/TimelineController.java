package com.defrainphoto.weddingtracker.controller;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.defrainphoto.weddingtracker.editors.SqlTimeEditor;
import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.Event;
import com.defrainphoto.weddingtracker.model.EventManager;
import com.defrainphoto.weddingtracker.model.TimeChunk;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

@Controller
public class TimelineController {
	@Autowired
	private TimelineManager timelineManager;
//	private TimelineManager timelineManager = new TimelineManager();
	@Autowired
	private EventManager eventManager;
//	private EventManager eventManager = new EventManager();
	
//	@RequestMapping(value = "/createTimeline/{eventId}/{startTime}", method = RequestMethod.GET)
//	public ModelAndView createTimeline(@PathVariable(value="eventId") String eventId,
//			@PathVariable(value="startTime") String startTime) {
//		
//		Timeline timeline = new Timeline();
//		timeline.setEventId(eventId);
//		timeline.setStartTime(Time.valueOf(startTime));
////		timeline.setTotalTime(Time.valueOf("0:0:0"));
//		
//		ModelAndView model = new ModelAndView("createTimeline", "command", timeline);
//		return model;
//	}
	
	@RequestMapping(value = {"/createTimeline/{eventId}/{startTime}", "/WeddingTracker/createTimeline/{eventId}/{startTime}"})
	public String createTimeline(@PathVariable(value="eventId") String eventId,
			@PathVariable(value="startTime") String startTime) {
		
		Timeline timeline = new Timeline();
		timeline.setEventId(eventId);
		timeline.setStartTime(Time.valueOf(startTime));
		timeline.setTotalTime(Time.valueOf("0:0:0"));
		
		timelineManager.addTimeline(timeline);
		
//		ModelAndView model = new ModelAndView("createTimeline", "command", timeline);

		return "timeline/listTimeSlices";
	}
	
	@RequestMapping(value = {"/addTimeline", "/WeddingTracker/addTimeline"}, method = RequestMethod.POST)
	public String addEvent(@ModelAttribute("timeline")Timeline timeline, ModelMap model) {
		
		timeline.setTotalTime(Time.valueOf("0:0:0"));
		model.addAttribute("startTime", 	timeline.getStartTime());
		model.addAttribute("eventId", 		timeline.getEventId());
		model.addAttribute("totalTime", 	timeline.getTotalTime());
		
		timelineManager.addTimeline(timeline);
		
		return "timeline/addTimeline";
	}
	
	@RequestMapping(value={"/listTimeSlices/{eventId}", "/WeddingTracker/listTimeSlices/{eventId}"}, method = RequestMethod.GET)
	public String getTimelineSlices(@PathVariable("eventId")String eventId, Model model) {
		
		List<TimeChunk> timeSlices = timelineManager.timeChunkManager.getAllChunksByEventId(eventId);
		
		Event event = new Event(eventId, "", null, null, null, null, null, "", "", null, null);
		event = eventManager.getEventById(event);
		
		Timeline timeline = new Timeline(eventId, null, null, null, null);
		timeline = timelineManager.getTimelineByEventId(timeline);
		
		Map<String, Client> clientMap = new HashMap<String, Client>();
		
		if (timeSlices != null) {
			for (TimeChunk tc : timeSlices) {
				tc.setTimeline(timeline);
				if (tc.getClient() != null) {
					clientMap.put(tc.getChunkId(), tc.getClient());
				}
			}
		}
		
		model.addAttribute("timeSlices", timeSlices);
		model.addAttribute("eventId", event.getEventId());		
		model.addAttribute("eventName", event.getEventName());
		model.addAttribute("clientMap", clientMap);
		return "timeline/listTimeSlices";
	}
	
	@RequestMapping(value={"/viewTimeline/{eventId}", "/WeddingTracker/viewTimeline/{eventId}"}, method = RequestMethod.GET)
	public String viewTimelineSlices(@PathVariable("eventId")String eventId, Model model) {
		
		List<TimeChunk> timeSlices = timelineManager.timeChunkManager.getAllChunksByEventId(eventId);
		
		Event event = new Event(eventId, "", null, null, null, null, null, "", "", null, null);
		event = eventManager.getEventById(event);
		
		Timeline timeline = new Timeline(eventId, null, null, null, null);
		timeline = timelineManager.getTimelineByEventId(timeline);
		
		Map<String, Client> clientMap = new HashMap<String, Client>();
		
		if (timeSlices != null) {
			for (TimeChunk tc : timeSlices) {
				tc.setTimeline(timeline);
				if (tc.getClient() != null) {
					clientMap.put(tc.getChunkId(), tc.getClient());
				}
			}
		}
		
		model.addAttribute("timeSlices", timeSlices);
		model.addAttribute("eventId", event.getEventId());		
		model.addAttribute("eventName", event.getEventName());
		model.addAttribute("clientMap", clientMap);
		return "timeline/viewTimeline";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
	}
	
}
