package com.defrainphoto.weddingtracker.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.defrainphoto.weddingtracker.model.MileageManager;

@Controller
public class MileageController {
	
	@Autowired
	private MileageManager mileageManager;

	@RequestMapping(value={"/listMileage", "/WeddingTracker/listMileage"}, method = RequestMethod.GET)
	public String getYearlyMileage(Map<String, Object> map) {
		
		map.put("yearlyMileage", mileageManager.getYearlyMileage());
		
		return "mileage/mileageHome";
	}
}
