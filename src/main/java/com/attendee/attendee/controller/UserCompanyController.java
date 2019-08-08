package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Company;
import com.attendee.attendee.model.UserCompany;
import com.attendee.attendee.service.UserCompanyService;

@Controller
@RestController
@RequestMapping(value = "/api/attendees")
public class UserCompanyController {
	@Autowired
	private UserCompanyService ucServ;
	
	@PostMapping(value = "/user-company")
	public ResponseEntity<?> insertCompany(@RequestBody UserCompany userCompany) throws Exception {
		List<MessageResponse> messagesSuccess = new ArrayList();
		List<MessageResponse> messagesExist = new ArrayList();
		List<MessageResponse> messagesException = new ArrayList();
	    List allMessages =  new ArrayList();
	    
		try {
			ucServ.save(userCompany);
			MessageResponse mr = new MessageResponse("Insert success!");
			messagesSuccess.add(mr);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Insert failed!");
			messagesException.add(mr);
		}
		
		if (messagesSuccess.size() > 0 && messagesExist.size() > 0) {
			allMessages.add(messagesSuccess);
			allMessages.add(messagesExist);
			return ResponseEntity.ok(allMessages);
		}

		if (messagesSuccess.size() > 0) {
			return ResponseEntity.ok(messagesSuccess);
		}

		if (messagesExist.size() > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesExist);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
	
	@PutMapping(value = "/user-company")
	public ResponseEntity<?> updateCompany(@RequestBody UserCompany userCompany) throws Exception {
		try {
			ucServ.update(userCompany);
			
			MessageResponse mr = new MessageResponse("Update success!");
			return ResponseEntity.ok(mr);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Update failed!");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
	
	@GetMapping(value = "/user-companies")
	public ResponseEntity<?> findAll() throws Exception {
		try {
			List<UserCompany> list = ucServ.findAll();
			return new ResponseEntity<List<UserCompany>>(list, HttpStatus.OK);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Retrieve failed!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
		}
	}
	
	@GetMapping(value = "/user-company")
	public ResponseEntity<?> findByFilter(@RequestBody UserCompany userCompany) throws Exception {
//		try {
			List<UserCompany> list = ucServ.findByFilter(userCompany);
			return ResponseEntity.ok(list);
//		} catch (Exception e) {
//			MessageResponse mr = new MessageResponse("Retrieve failed!");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
//		}
	}

}
