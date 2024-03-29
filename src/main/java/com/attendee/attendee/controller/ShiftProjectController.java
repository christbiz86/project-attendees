package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.ShiftProject;
import com.attendee.attendee.service.ShiftProjectService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping({"/api"})
@Controller
public class ShiftProjectController {
	@Autowired
	private ShiftProjectService shiftProjectService;
	
	@GetMapping(value = "shift-project")
	public @ResponseBody List<ShiftProject> getAllShiftProject(){
		List<ShiftProject> shiftProjectList = shiftProjectService.findAll();
		return shiftProjectList;
	}
	
	@PostMapping(value = "shift-project")
	public ResponseEntity<?> insertShiftProject(@RequestBody ShiftProject shiftProject) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			shiftProjectService.save(shiftProject);
			MessageResponse mg = new MessageResponse("Insert Success");
			messagesSuccess.add(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Insert Failed");
			messagesException.add(mg);
		}
		
		if(messagesFailed.size() > 0 && messagesSuccess.size() > 0) {
			allMessages.add(messagesSuccess);
			allMessages.add(messagesFailed);
			return ResponseEntity.ok(allMessages);
		}
		
		if(messagesSuccess.size() > 0) {
			return ResponseEntity.ok(messagesSuccess);
			
		}
		
		if(messagesFailed.size() > 0 ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesFailed);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
	
	@PutMapping(value = "shift-project")
	public ResponseEntity<?> submitUpdate(@RequestBody ShiftProject shiftProject) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			shiftProjectService.update(shiftProject);
			MessageResponse mg = new MessageResponse("Update Success");
			messagesSuccess.add(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Update Failed");
			messagesException.add(mg);
		}
		
		if(messagesSuccess.size() > 0) {
			return ResponseEntity.ok(messagesSuccess);
			
		}
		
		if(messagesFailed.size() > 0 ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesFailed);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
}
