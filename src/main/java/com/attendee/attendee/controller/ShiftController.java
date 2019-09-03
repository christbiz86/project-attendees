package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.exception.MessageListResponse;
import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Shift;
import com.attendee.attendee.service.ShiftService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
@Controller
public class ShiftController {
	@Autowired
	private ShiftService shiftService;
	
	@GetMapping(value = "shift")
	public @ResponseBody List<Shift> getAllShift(){
		List<Shift> shiftList = shiftService.findAll();
		return shiftList;
	}
	
	@GetMapping(value = "shift/{status}")
	public @ResponseBody List<Shift> getFilterShift(@PathVariable String status) throws Exception {
		List<Shift> shiftList = shiftService.filterShift(status);
		return shiftList;
	}
	
	@PostMapping(value = "shift")
	public ResponseEntity<?> insertShift(@RequestBody Shift shift) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			shiftService.save(shift);
			MessageResponse mg = new MessageResponse("Insert Success with Code Shift " + shift.getKode());
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
			
		} else if(messagesFailed.size() > 0 ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesFailed);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
	
	@PutMapping(value = "shift")
	public ResponseEntity<?> submitUpdate(@RequestBody Shift shift) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			shiftService.update(shift);
			MessageResponse mg = new MessageResponse("Update Success With Code Shift " + shift.getKode());
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
