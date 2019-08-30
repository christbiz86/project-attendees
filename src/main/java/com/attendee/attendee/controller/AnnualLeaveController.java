<<<<<<< HEAD
package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.model.AnnualLeave;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.AnnualLeaveService;

@CrossOrigin(origins = "*")
@RequestMapping({"/annual"})
@Controller
@RestController
public class AnnualLeaveController {
	@Autowired
	private AnnualLeaveService anLevServ;
	
	@PostMapping(value = "/saldo")
	public ResponseEntity<?> findByFilter(@RequestBody AnnualLeave annualLeave) throws Exception {
		try {
			annualLeave.setIdCompany(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getId());
			List<AnnualLeave> annualLeaveList = anLevServ.findByFilter(annualLeave);
			return ResponseEntity.ok(annualLeaveList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
=======
package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.model.AnnualLeave;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.AnnualLeaveService;

@CrossOrigin(origins = "*")
@RequestMapping({"/annual"})
@Controller
@RestController
public class AnnualLeaveController {
	@Autowired
	private AnnualLeaveService anLevServ;
	
	@PostMapping(value = "/saldo")
	public ResponseEntity<?> findByFilter(@RequestBody AnnualLeave annualLeave) throws Exception {
		try {
			annualLeave.setIdCompany(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getId());
			List<AnnualLeave> annualLeaveList = anLevServ.findByFilter(annualLeave);
			return ResponseEntity.ok(annualLeaveList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
