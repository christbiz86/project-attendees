package com.attendee.attendee.controller;

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

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Absen;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.AttendeeService;

@CrossOrigin(origins= "*")
@Controller
@RestController
@RequestMapping("/user")
public class AttendeeController {
	
	@Autowired
	private AttendeeService attendeeService;
	
	@PostMapping("/absen")
	public ResponseEntity<?> absen(@RequestBody Absen absen) throws ValidationException,Exception {
		
		try {
			attendeeService.saveAbsen(absen, ((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany());
			
			MessageResponse mg  = new MessageResponse("Success submit");
			return ResponseEntity.ok(mg);
			
		}catch(ValidationException val) {
			
			System.out.println(val); 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
			
		}catch (Exception e) {
		
			System.out.println(e);
			MessageResponse mg = new MessageResponse("Failed submit" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	    
	}

}
