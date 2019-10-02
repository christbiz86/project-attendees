package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Attendee;
import com.attendee.attendee.model.PojoAbsen;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.AttendeeService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping({"/user"})
public class AttendeeController {
	@Autowired
	private AttendeeService attendeeService;
	
	@Transactional
	@PostMapping(value = "/absen")
	public ResponseEntity<?> absen(@RequestBody PojoAbsen absen) throws Exception {
		try {
			attendeeService.saveAbsen(absen, ((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany());
			return ResponseEntity.ok("berhasil");
		} catch(ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
		}
	}
	
	@Transactional
	@PostMapping(value = "/attendee")
	public ResponseEntity<?> retrieveByFilter(@RequestBody Attendee attendee) throws Exception {
		try {
			attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().setIdCompany(
					((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			List<Attendee> list = attendeeService.findByFilter(attendee);
			return ResponseEntity.ok(list);
		} catch(ValidationException val) {
			System.out.println(val);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
		}catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
		}
	}
}
