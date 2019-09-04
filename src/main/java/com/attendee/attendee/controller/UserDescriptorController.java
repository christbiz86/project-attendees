package com.attendee.attendee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.model.UserDescriptor;
import com.attendee.attendee.service.UserDescriptorService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping({"/user"})
public class UserDescriptorController {
	@Autowired
	private UserDescriptorService usdServ;
	
	@PostMapping(value="/descriptor/register")
	public ResponseEntity<?> register(@RequestBody UserDescriptor userDescriptor) throws Exception {
		try {
			usdServ.add(userDescriptor);
			return ResponseEntity.ok("berhasil");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
		}
	}
	
	@PostMapping(value = "/descriptor")
	public ResponseEntity<?> getDescriptor(@RequestBody UserDescriptor userDescriptor) throws Exception{
		try {
			return ResponseEntity.ok(usdServ.getDescriptor(userDescriptor));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
		}
	}
}