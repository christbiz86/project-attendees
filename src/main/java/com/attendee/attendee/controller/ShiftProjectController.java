package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.ShiftProject;
import com.attendee.attendee.service.ShiftProjectService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
@Controller
public class ShiftProjectController {
	@Autowired
	private ShiftProjectService shiftProjectService;
	
	@PostMapping(value = "/shift-project")
	public ResponseEntity<?> insertShiftProject(@RequestBody ShiftProject shiftProject) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			shiftProjectService.save(shiftProject);
			MessageResponse mg = new MessageResponse("Insert Success with Code Project " + shiftProject.getProject().getKode() + " And Code Shift " + shiftProject.getShift().getKode());
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
			
		} else if(messagesFailed.size() > 0 ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesFailed);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
	
	@DeleteMapping(value = "/shift-project/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id) throws Exception {
		try {
			shiftProjectService.delete(id);
			MessageResponse mg = new MessageResponse("Delete Success");
			return ResponseEntity.ok(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Delete Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
}
