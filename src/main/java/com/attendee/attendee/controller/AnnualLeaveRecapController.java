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

import com.attendee.attendee.model.AnnualLeaveRecap;
import com.attendee.attendee.service.AnnualLeaveRecapService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping({"/annual"})
public class AnnualLeaveRecapController {
	@Autowired
	private AnnualLeaveRecapService alpServ;
	
	@GetMapping(value = "/company/{company}/start-date/{startDate}/end-date/{endDate}")
	public @ResponseBody List<AnnualLeaveRecap> getAll(@PathVariable String company, 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
		List<AnnualLeaveRecap> attendeeList = alpServ.getAll(company, startDate, endDate);
		return attendeeList;
	}
}
