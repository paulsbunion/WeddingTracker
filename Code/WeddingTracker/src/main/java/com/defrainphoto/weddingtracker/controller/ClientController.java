package com.defrainphoto.weddingtracker.controller;

import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.ClientManager;

@Controller
public class ClientController {
	@Autowired
	private ClientManager clientManager;
//	private ClientManager clientManager = new ClientManager();
	
	@RequestMapping(value = {"/createClient", "/WeddingTracker/createClient" }, method = RequestMethod.GET)
	public ModelAndView createClient() {
		
		ModelAndView model = new ModelAndView("client/createClient", "client", new Client());
		
		String[] yNChoice = {"N", "Y"};
		
		model.addObject("yNChoice", yNChoice);
		
		return model;
	}
	
	@RequestMapping(value = {"/addClient", "/WeddingTracker/addClient"}, method = RequestMethod.POST)
	public String addClient(@Valid @ModelAttribute("client")Client client, BindingResult result,
			@RequestParam(value = "submitCancelParam") String submitCancel, ModelMap model) {
		
		String returnValue = "client/addClient";
		
		// if cancel, go to list events page
		if (submitCancel.equalsIgnoreCase("cancel")) {
			returnValue = "redirect:/listClients";
		}

		else if (result.hasErrors()) {
			String[] yNChoice = {"N", "Y"};
			
			model.addAttribute("yNChoice", yNChoice);
			returnValue = "client/createClient";
		}
		else {	
			model.addAttribute("firstName", 	client.getFirstName());
			model.addAttribute("lastName",		client.getLastName());
			model.addAttribute("address", 	client.getAddress());
			model.addAttribute("phoneNumber",		client.getPhoneNumber());
			model.addAttribute("email",		client.getEmail());
			model.addAttribute("autoRemind",		client.getAutoRemind());
			model.addAttribute("events", 		client.getEvents());
			
			client.setClientId("");
			client.setEvents(null);
			
			try {
				client = clientManager.addClient(client);
			}
			catch (EntityExistsException e) {
				
			}
			
			model.addAttribute("clientId", client.getClientId());
		}
		
		return returnValue;
	}
	
	@RequestMapping(value ={"/editClient/{clientId}", "/WeddingTracker/editClient/{clientId}"}, method=RequestMethod.GET)
	public ModelAndView editClient(@PathVariable("clientId") String clientId) {
		
		Client client = new Client(clientId, "", "", "", "", "", "");
		client = clientManager.getClientById(client);
		
		ModelAndView model = new ModelAndView("client/editClient", "client", client);
		model.addObject("clientId", clientId);
		String[] yNChoice = {"N", "Y"};
		model.addObject("yNChoice", yNChoice);
		
		return model;
	}
	
	@RequestMapping(value={"/editClient", "/WeddingTracker/editClient"}, method = RequestMethod.POST)
	public String editClientSaved(@Valid @ModelAttribute("client")Client client, BindingResult result,
			@RequestParam(value = "submitCancelParam") String submitCancel, ModelMap model) {
		
		String returnValue = "client/editClientSaved";
		
		// if cancel, go to list events page
		if (submitCancel.equalsIgnoreCase("cancel")) {
			returnValue = "redirect:/listClients";
		}
		else if (result.hasErrors()) {
			String[] yNChoice = {"N", "Y"};
			
			model.addAttribute("yNChoice", yNChoice);
			returnValue = "client/editClient";
		}
		else {			
			model.addAttribute("firstName", 	client.getFirstName());
			model.addAttribute("lastName",		client.getLastName());
			model.addAttribute("address", 	client.getAddress());
			model.addAttribute("phoneNumber",		client.getPhoneNumber());
			model.addAttribute("email",		client.getEmail());
			model.addAttribute("autoRemind",		client.getAutoRemind());
			model.addAttribute("events", 		client.getEvents());
			
			System.out.println("the client");
			System.out.println(model.toString());
			System.out.println(client);
			System.out.println(client.getClientId());
	//		client.setClientId("");
	//		client.setEvents(null);
	//		client = clientManager.(client);
			
			try {
				clientManager.updateClient(client);
			}
			catch (EntityExistsException e) {
				
			}
			
			model.addAttribute("clientId", client.getClientId());
		}
		
		return returnValue;
	}
	
	
	@RequestMapping(value ={"/listClients", "/WeddingTracker/listClients"})
	public String listClients(Map<String, Object> map) {
		
		
		map.put("clientList", clientManager.getAllClients());
		
		return "client/listClients";
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(EventType.class, new EventTypeEditor());
//		binder.registerCustomEditor(Time.class, new SqlTimeEditor());
//	}
}
