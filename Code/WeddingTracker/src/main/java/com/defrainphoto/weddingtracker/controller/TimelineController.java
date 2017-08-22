package com.defrainphoto.weddingtracker.controller;

import java.sql.Time;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.editors.SqlTimeEditor;
import com.defrainphoto.weddingtracker.model.Event;
import com.defrainphoto.weddingtracker.model.EventManager;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

@Controller
public class TimelineController {
	private TimelineManager timelineManager = new TimelineManager();
	private EventManager eventManager = new EventManager();
	
	@RequestMapping(value = "/createTimeline/{eventId}/{startTime}", method = RequestMethod.GET)
	public ModelAndView createTimeline(@PathVariable(value="eventId") String eventId,
			@PathVariable(value="startTime") String startTime) {
		
		Timeline timeline = new Timeline();
		timeline.setEventId(eventId);
		timeline.setStartTime(Time.valueOf(startTime));
//		timeline.setTotalTime(Time.valueOf("0:0:0"));
		System.out.println("the eventId in get");
		System.out.println(eventId);
		ModelAndView model = new ModelAndView("createTimeline", "command", timeline);
		return model;
	}
	
	@RequestMapping(value = "/addTimeline", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute("WeddingTracker")Timeline timeline, ModelMap model) {
		
		timeline.setTotalTime(Time.valueOf("0:0:0"));
		model.addAttribute("startTime", 	timeline.getStartTime());
		model.addAttribute("eventId", 		timeline.getEventId());
		model.addAttribute("totalTime", 	timeline.getTotalTime());
		System.out.println("Timeline Error");
		System.out.println(timeline);
		System.out.println(timeline.getEventId());
		
		timelineManager.addTimeline(timeline);
		
		return "addTimeline";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
	}
	
}
