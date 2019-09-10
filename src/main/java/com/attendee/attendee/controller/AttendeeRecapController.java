package com.attendee.attendee.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Attendee;
import com.attendee.attendee.model.AttendeeRecap;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.AttendeeRecapService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping({"/attendee"})
public class AttendeeRecapController {
	@Autowired
	private AttendeeRecapService attendeeRecapService;
	
	@GetMapping(value = "/recap/start-date/{startDate}/end-date/{endDate}")
	public @ResponseBody List<AttendeeRecap> getAll(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
		List<AttendeeRecap> attendeeList = attendeeRecapService.getAll(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getNama(), startDate, endDate);
		return attendeeList;
	}
	
	@PostMapping(value = "/recap")
	public ResponseEntity<?> getbyUSP(@RequestBody Attendee att) throws Exception {
		try {
			List<Attendee> list = attendeeRecapService.getByUSP(att);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}

	@GetMapping(value = "/recap/start-date/{startDate}/end-date/{endDate}/report")
	public ResponseEntity<?> generateReport(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) throws Exception {
		try {
			byte[] pdf = attendeeRecapService.generateReport(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
					.getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getNama(), startDate, endDate);
			return ResponseEntity.ok(pdf);
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("Report making failed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
	
//	@GetMapping(value = "/recap/jxl")
//	public ResponseEntity<?> generateJxl() throws Exception {
//		try {
//			attendeeRecapService.writeExcel();
//			MessageResponse mr = new MessageResponse("Report making success!");
//			return ResponseEntity.ok(mr);
//		} catch (Exception e) {
//			System.out.println(e);
//			MessageResponse mr = new MessageResponse("Report making failed!");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
//		}
//	}
	
//	@GetMapping(value = "/recap/poi")
//	public ResponseEntity<?> generatePOI() throws Exception {
//		try {
//			attendeeRecapService.writeReport();
//			MessageResponse mr = new MessageResponse("Report making success!");
//			return ResponseEntity.ok(mr);
//		} catch (Exception e) {
//			System.out.println(e);
//			MessageResponse mr = new MessageResponse("Report making failed!");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
//		}
//	}
	
	@GetMapping(value = "/recap/poi-format")
	public ResponseEntity<?> generatePOIFormat() throws Exception {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = dateFormat.parse("2019-01-01");
			Date endDate = dateFormat.parse("2020-12-31");

			System.out.println(startDate);
			System.out.println(endDate);
			List<AttendeeRecap> attendeeList = getAll(startDate, endDate);
			attendeeRecapService.writePOI(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
					.getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getNama(), startDate, endDate);
			MessageResponse mr = new MessageResponse("Report making success!");
			return ResponseEntity.ok(attendeeList);
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("Report making failed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
}
