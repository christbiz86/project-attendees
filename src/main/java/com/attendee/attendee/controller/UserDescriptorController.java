package com.attendee.attendee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.attendee.attendee.model.UserDescriptorPojo;
import com.attendee.attendee.service.CloudService;
import com.attendee.attendee.service.UserDescriptorService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping({"/user"})
public class UserDescriptorController {
	@Autowired
	private UserDescriptorService usdServ;
	
	@Autowired
	private CloudService cloudService;
	
	@PostMapping(value = "/descriptor/register/json")
	public ResponseEntity<?> registerJson(@RequestBody UserDescriptorPojo userDescriptor) throws Exception {
		try {
			usdServ.addJson(userDescriptor);
			return ResponseEntity.ok("berhasil");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
		}
	}
	
	@PostMapping(value = "/descriptor/register/image")
	public ResponseEntity<?> registerImage(@RequestParam("file") MultipartFile file, 
            @RequestParam("id") String id) throws Exception {
		try {
			cloudService.save(file, id);
			return ResponseEntity.ok("berhasil");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
		}
	}
	
	@GetMapping(value = "/descriptor/{id}")
	public ResponseEntity<?> getDescriptor(@PathVariable String id) throws Exception{
		try {
			return ResponseEntity.ok(usdServ.getDescriptor(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
		}
	}
}