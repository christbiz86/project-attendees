package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.attendee.attendee.model.AnnualLeave;
import com.attendee.attendee.service.AnnualLeaveService;

@CrossOrigin(origins = "*")
@RequestMapping({"/annual"})
@Controller
@RestController
public class AnnualLeaveController {
	@Autowired
	private AnnualLeaveService anLevServ;
	
	@GetMapping(value = "/saldo")
	public ResponseEntity<?> findByFilter(@RequestBody AnnualLeave annualLeave) throws Exception {
		try {
			List<AnnualLeave> annualLeaveList = anLevServ.findByFilter(annualLeave);
			return ResponseEntity.ok(annualLeaveList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
