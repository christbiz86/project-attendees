package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.exception.MessageListResponse;
import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Project;
import com.attendee.attendee.model.Unit;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.ProjectService;
import com.attendee.attendee.service.StatusService;
import com.attendee.attendee.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private StatusService stServ;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "project")
	public @ResponseBody List<Project> getAllProject(){
//		List<Project> projectList = projectService.findAll("Active");
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
		try {
			project.setStatus(stServ.findByStatus(project.getStatus().getStatus()));
			
			projectService.update(project);
			MessageResponse mg = new MessageResponse("Update Success With Code Project " + project.getKode());
			return ResponseEntity.ok(mg);
		} 
		
		catch (ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());

		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Failed update");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@PutMapping(value = "projects")
	public ResponseEntity<?> submitUpdates(@RequestBody HashMap<Integer, Project> pro) throws Exception {
		try {
			for (Map.Entry<Integer, Project> entry : pro.entrySet()) {
				Project p = entry.getValue();
				p.setUpdatedBy(userService.findById(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
				
				projectService.update(p);
			}
			
			MessageResponse mg = new MessageResponse("Update Success!");
			return ResponseEntity.ok(mg);
		} 
		
		catch (ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
		}
		catch (Exception e) {
			MessageResponse mg = new MessageResponse("Update failed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
}
