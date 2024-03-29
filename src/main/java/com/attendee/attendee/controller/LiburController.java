package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Libur;
import com.attendee.attendee.model.LiburCompany;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.LiburCompanyService;
import com.attendee.attendee.service.LiburService;
import com.attendee.attendee.service.StatusService;

@CrossOrigin(origins= "*",  allowedHeaders = "*")
@Controller
@RestController
@RequestMapping("/attendees/libur")
public class LiburController {

	@Autowired
	private LiburService liburService;
	
	@Autowired
	private LiburCompanyService lcServ;
	
	@Autowired
	private StatusService stServ;
	
	@PostMapping
	public ResponseEntity<?> insertLibur(@RequestBody Libur libur) throws Exception {
		List<MessageResponse> messagesSuccess = new ArrayList();
		List<MessageResponse> messagesExist = new ArrayList();
		List<MessageResponse> messagesException = new ArrayList();
	    List allMessages =  new ArrayList();
	    
		try {
			liburService.save(libur);
			try {
				libur = liburService.findByBk(libur);
				LiburCompany lc = new LiburCompany();
				lc.setLibur(libur);
				lc.setCompany(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
				
				lcServ.insert(lc);
				
				MessageResponse mr = new MessageResponse("Insert success with libur name "+libur.getNama());
				messagesSuccess.add(mr);
			} catch (Exception e) {
				System.out.println(e);
				MessageResponse mr = new MessageResponse("Insert all failed!");
				messagesException.add(mr);
			}
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("Insert libur failed!");
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
	
	@PutMapping
	public ResponseEntity<?> updateLibur(@RequestBody Libur libur) throws Exception {
		List<MessageResponse> messagesSuccess = new ArrayList();
		List<MessageResponse> messagesExist = new ArrayList();
		List<MessageResponse> messagesException = new ArrayList();
	    List allMessages =  new ArrayList();
	    
		try {
			libur.setStatus(stServ.findByStatus(libur.getStatus().getStatus()));
			
			liburService.update(libur);
			MessageResponse mr = new MessageResponse("Update success with libur name "+libur.getNama());
			messagesSuccess.add(mr);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Update failed!");
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
	
	@GetMapping
	public ResponseEntity<?> findAll() throws Exception {
		try {
			List<Libur> list = liburService.findAll();
			return new ResponseEntity<List<Libur>>(list, HttpStatus.OK);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Retrieve failed!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
		}
	}
	
	@GetMapping(value= "/filter")
	public ResponseEntity<?> findByFilter(@RequestBody Libur libur) throws Exception {
		try {
			List<Libur> list = liburService.findByFilter(libur);
			return new ResponseEntity<List<Libur>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("Retrieve failed!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
		}
	}
}
