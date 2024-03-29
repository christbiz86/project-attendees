package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.model.UserShiftProject;
import com.attendee.attendee.service.UserShiftProjectService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping({"/api"})
@Controller
public class UserShiftProjectController {
	@Autowired
	private UserShiftProjectService uspService;
	
	@GetMapping(value = "user-shift-project")
	public @ResponseBody List<UserShiftProject> getAllUserShiftProject(){
		List<UserShiftProject> userShiftProjectList = uspService.findAll();
		return userShiftProjectList;
	}
	
//	@GetMapping(value = "user-shift-project")
//	public @ResponseBody List<UserShiftProject> getFilterUserShift(@PathVariable String worktime) throws Exception {
//		List<UserShiftProject> userShiftList = userShiftProjectService.filterUserShift(worktime);
//		return userShiftList;
//	}
	
	@PostMapping(value = "user-shift-project")
	public ResponseEntity<?> insertUserShiftProject(@RequestBody UserShiftProject userShiftProject) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			uspService.save(userShiftProject);
			MessageResponse mg = new MessageResponse("Insert Success with User " + userShiftProject.getUserCompany().getIdUser().getNama() + " And Project Code " + userShiftProject.getShiftProject().getProject().getKode());
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
	
	@DeleteMapping(value = "user-shift-project/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id) throws Exception {
		try {
			uspService.delete(id);
			MessageResponse mg = new MessageResponse("Delete Success");
			return ResponseEntity.ok(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Delete Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@RequestMapping(value = "/shift/filter", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByFilter(@RequestBody UserShiftProject usp) throws ValidationException
	{
		 try 
		 {
			 usp.getUserCompany().getIdCompanyUnitPosisi().setIdCompany(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			 List<UserShiftProject> list = uspService.findByFilter(usp);

			 return ResponseEntity.ok(list);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}
}
