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
import com.attendee.attendee.model.AnnualLeaveRecap;
import com.attendee.attendee.model.Request;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.AnnualLeaveRecapService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping({"/annual"})
public class AnnualLeaveRecapController {
	@Autowired
	private AnnualLeaveRecapService alpServ;
	
	@GetMapping(value = "/recap/start-date/{startDate}/end-date/{endDate}")
	public @ResponseBody List<AnnualLeaveRecap> getAll(
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
		List<AnnualLeaveRecap> attendeeList = alpServ.getAll(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getNama(), startDate, endDate);
		return attendeeList;
	}
	
	@GetMapping(value = "/recap/start-date/{startDate}/end-date/{endDate}/report/pdf")
	public ResponseEntity<?> generateReportPdf( 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) throws Exception {
		try {
			byte[] pdf =alpServ.generateReport(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getNama(), startDate, endDate);
			return ResponseEntity.status(HttpStatus.OK).body(pdf);
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("Report making failed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
	
	@PostMapping(value = "/recap")
	public ResponseEntity<?> getByUC (@RequestBody Request request) {
		try {
			List<Request> list = alpServ.getByUC(request);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@GetMapping(value = "/recap/start-date/{startDate}/end-date/{endDate}/report/excel")
	public ResponseEntity<?> generateReportXls( 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) throws Exception {
		try {
			byte[] excel = alpServ.writePOI(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
					.getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getNama(), startDate, endDate);
			return ResponseEntity.ok(excel);
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("Report making failed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
}
