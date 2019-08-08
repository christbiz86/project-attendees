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

import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.exception.MessageListResponse;
import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Project;
import com.attendee.attendee.service.ProjectService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@GetMapping(value = "project")
	public @ResponseBody List<Project> getAllProject(){
		List<Project> projectList = projectService.findAll();
		return projectList;
	}
	
	@GetMapping(value = "project/filter")
	public @ResponseBody List<Project> getFilterProject(@RequestBody Project project) throws Exception {
		List<Project> proList = projectService.filterProject(project.getStatus().getStatus(), project.getLokasi());
		return proList;
	}
	
	@PostMapping(value = "project")
	public ResponseEntity<?> insertProject(@RequestBody Project project) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			projectService.save(project);
			MessageResponse mg = new MessageResponse("Insert Success with Code Shift " + project.getKode());
			messagesSuccess.add(mg);
		} catch (InvalidDataException ex) {
			MessageListResponse mg = new MessageListResponse(ex.getMessages());
			messagesFailed.add(mg);
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
	
	@PutMapping(value = "project")
	public ResponseEntity<?> submitUpdate(@RequestBody Project project) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			projectService.update(project);
			MessageResponse mg = new MessageResponse("Update Success With Code Project " + project.getKode());
			messagesSuccess.add(mg);
		} catch (InvalidDataException ex) {
			MessageListResponse mg = new MessageListResponse(ex.getMessages());
			messagesFailed.add(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Update Failed");
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
}
