package com.defrainphoto.weddingtracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.ClientManager;

@Controller
public class ClientController {
	private ClientManager clientManager = new ClientManager();
	
	@RequestMapping(value = "/createClient", method = RequestMethod.GET)
	public ModelAndView createClient() {
		
		ModelAndView model = new ModelAndView("createClient", "command", new Client());
		
		String[] yNChoice = {"N", "Y"};
		
		model.addObject("yNChoice", yNChoice);
		
		return model;
	}
	
	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public String addClient(@ModelAttribute("Client")Client client, ModelMap model) {
		
		model.addAttribute("firstName", 	client.getFirstName());
		model.addAttribute("lastName",		client.getLastName());
		model.addAttribute("address", 	client.getAddress());
		model.addAttribute("phoneNumber",		client.getPhoneNumber());
		model.addAttribute("email",		client.getEmail());
		model.addAttribute("autoRemind",		client.getAutoRemind());
		model.addAttribute("events", 		client.getEvents());
		
		client.setClientId("");
		client.setEvents(null);
		client = clientManager.addClient(client);
		
		model.addAttribute("clientId", client.getClientId());
		return "addClient";
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(EventType.class, new EventTypeEditor());
//		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
//	}
}
