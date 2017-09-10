package com.defrainphoto.weddingtracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.model.EventPhotographer;
import com.defrainphoto.weddingtracker.model.EventPhotographerManager;
import com.defrainphoto.weddingtracker.model.Photographer;
import com.defrainphoto.weddingtracker.model.PhotographerManager;
import com.defrainphoto.weddingtracker.model.TimelineManager;

@Controller
public class PhotographerController {
	@Autowired
	private PhotographerManager photographerManager;
	// private PhotographerManager photographerManager = new
	// PhotographerManager();
	@Autowired
	private EventPhotographerManager eventPhotographerManager;
	// private EventPhotographerManager eventPhotographerManager = new
	// EventPhotographerManager();
	@Autowired
	private TimelineManager timelineManager;
	// private TimelineManager timelineManager = new TimelineManager();

	@RequestMapping(value = { "/createPhotographer", "/WeddingTracker/createPhotographer" }, method = RequestMethod.GET)
	public ModelAndView createPhotographer() {

		ModelAndView model = new ModelAndView("photographer/createPhotographer", "command", new Photographer());

		// String[] yNChoice = {"N", "Y"};
		//
		// model.addObject("yNChoice", yNChoice);

		return model;
	}

	@RequestMapping(value = { "/addPhotographer", "/WeddingTracker/addPhotographer" }, method = RequestMethod.POST)
	public String addPhotographer(@ModelAttribute("Photographer") Photographer photographer, ModelMap model) {

		model.addAttribute("firstName", photographer.getFirstName());
		model.addAttribute("lastName", photographer.getLastName());
		model.addAttribute("events", photographer.getEvents());

		photographer.setStaffId("");
		photographer.setEvents(null);
		photographer = photographerManager.addPhotographer(photographer);

		model.addAttribute("staffId", photographer.getStaffId());
		return "photographer/addPhotographer";
	}

	@RequestMapping(value = { "/editPhotographer/{staffId}",
			"/WeddingTracker/editPhotographer/{staffId}" }, method = RequestMethod.GET)
	public ModelAndView editPhotographer(@PathVariable("staffId") String staffId) {

		Photographer photographer = new Photographer(staffId, "", "");
		photographer = photographerManager.getPhotographerById(photographer);

		System.out.println("here");
		System.out.println(staffId);
		System.out.println(photographer.getStaffId());

		ModelAndView model = new ModelAndView("photographer/editPhotographer", "command", photographer);

		return model;
	}

	@RequestMapping(value = { "/editPhotographer", "/WeddingTracker/editPhotographer" }, method = RequestMethod.POST)
	public String editPhotographerSaved(@ModelAttribute("Photographer") Photographer photographer, ModelMap model) {
		model.addAttribute("firstName", photographer.getFirstName());
		model.addAttribute("lastName", photographer.getLastName());
		model.addAttribute("staffId", photographer.getStaffId());

		photographerManager.updatePhotographer(photographer);

		System.out.println("the photog");
		System.out.println(photographer);
		return "photographer/editPhotographerSaved";
	}

	@RequestMapping(value = { "/listPhotographers", "/WeddingTracker/listPhotographers" })
	public String listPhotographers(Map<String, Object> map) {

		map.put("photographerList", photographerManager.getAllPhotographers());

		return "photographer/listPhotographers";
	}

	@RequestMapping(value = { "/photographerEvents/{staffId}",
			"/WeddingTracker/photographerEvents/{staffId}" }, method = RequestMethod.GET)
	public String getPhotographerEvents(@PathVariable("staffId") String staffId, Model model) {

		List<EventPhotographer> photographerEvents = eventPhotographerManager.getPhotographerEvents(staffId);

		// get timeline id's
		model.addAttribute("timelineIdMap", timelineManager.getallTimelineIds());

		model.addAttribute("photographerEvents", photographerEvents);
		if (photographerEvents != null) {
			model.addAttribute("photographer", photographerEvents.get(0).getPhotographer());
		} else {
			Photographer temp = new Photographer(staffId, "", "");

			temp = photographerManager.getPhotographerById(temp);
			model.addAttribute("photographer", temp);
		}

		return "photographer/photographerEvents";
	}

	// @InitBinder
	// public void initBinder(WebDataBinder binder) {
	// binder.registerCustomEditor(EventType.class, new EventTypeEditor());
	// binder.registerCustomEditor(Time.class, new SqlTimeEditor());
	// }
}
