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
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Company;
import com.attendee.attendee.service.CompanyService;

@CrossOrigin(origins = "*")
@Controller
@RestController
@RequestMapping("/api/attendees")
public class CompanyController {
	@Autowired
	private CompanyService comServ;
	
	@PostMapping(value = "/company")
	public ResponseEntity<?> insertCompany(@RequestBody Company company) throws Exception {
		List<MessageResponse> messagesSuccess = new ArrayList();
		List<MessageResponse> messagesExist = new ArrayList();
		List<MessageResponse> messagesException = new ArrayList();
	    List allMessages =  new ArrayList();
	    
		try {
			comServ.insert(company);
			MessageResponse mr = new MessageResponse("Insert success with company name "+company.getNama());
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
	
	@PutMapping(value = "/company")
	public ResponseEntity<?> updateCompany(@RequestBody Company company) throws Exception {
		try {
			comServ.update(company);
			
			MessageResponse mr = new MessageResponse("Update success company code "+company.getKode()+" and name "+company.getNama());
			return ResponseEntity.ok(mr);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Update failed!");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
	
	@GetMapping(value = "/companies")
	public ResponseEntity<?> findAll() throws Exception {
		try {
			List<Company> list = comServ.findAll();
			return new ResponseEntity<List<Company>>(list, HttpStatus.OK);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Retrieve failed!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
		}
	}
	
//	@GetMapping(value = "/company/{kode}")
//	public ResponseEntity<?> findByBk(@PathVariable String kode) throws Exception {
//		try {
//			Company c = comServ.findByBk(kode);
//			return new ResponseEntity<Company>(c, HttpStatus.OK);
//		} catch (Exception e) {
//			MessageResponse mr = new MessageResponse("Retrieve failed!");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
//		}
//	}
	
	@GetMapping(value = "/company")
	public ResponseEntity<?> findByFilter(@RequestBody Company company) throws Exception {
//		try {
			Company list = comServ.findByFilter(company);
			return ResponseEntity.ok(list);
//		} catch (Exception e) {
//			MessageResponse mr = new MessageResponse("Retrieve failed!");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
//		}
	}
		
}
