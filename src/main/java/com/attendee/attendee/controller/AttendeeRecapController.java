package com.attendee.attendee.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.model.AttendeeRecap;
import com.attendee.attendee.service.AttendeeRecapService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping({"/api"})
public class AttendeeRecapController {
	@Autowired
	private AttendeeRecapService attendeeRecapService;
	
	@GetMapping(value = "/attendee-recap/start-date/{startDate}/end-date/{endDate}")
	public @ResponseBody List<AttendeeRecap> getAll(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
		List<AttendeeRecap> attendeeList = attendeeRecapService.getAll(startDate, endDate);
		return attendeeList;
	}
}