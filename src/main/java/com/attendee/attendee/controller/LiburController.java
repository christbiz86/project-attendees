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
import com.attendee.attendee.model.Libur;
import com.attendee.attendee.service.LiburService;

@CrossOrigin(origins= "*")
@Controller
@RestController
@RequestMapping("/attendees/libur")
public class LiburController {

	@Autowired
	private LiburService liburService;
	
	@PostMapping
	public ResponseEntity<?> insertLibur(@RequestBody List<Libur> libur) throws Exception {
		List<MessageResponse> messagesSuccess = new ArrayList();
		List<MessageResponse> messagesExist = new ArrayList();
		List<MessageResponse> messagesException = new ArrayList();
	    List allMessages =  new ArrayList();
	    
	    for (Libur list : libur) {
			try {
				liburService.save(list);
				MessageResponse mr = new MessageResponse("Insert success with libur name "+list.getNama());
				messagesSuccess.add(mr);
			} catch (Exception e) {
				System.out.println(e);
				MessageResponse mr = new MessageResponse("Insert failed!");
				messagesException.add(mr);
			}
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
	public ResponseEntity<?> updateLibur(@RequestBody List<Libur> libur) throws Exception {
		List<MessageResponse> messagesSuccess = new ArrayList();
		List<MessageResponse> messagesExist = new ArrayList();
		List<MessageResponse> messagesException = new ArrayList();
	    List allMessages =  new ArrayList();
	    
	    for (Libur list : libur) {
			try {
				liburService.update(list);
				MessageResponse mr = new MessageResponse("Update success with libur name "+list.getNama());
				messagesSuccess.add(mr);
			} catch (Exception e) {
				MessageResponse mr = new MessageResponse("Update failed!");
				messagesException.add(mr);
			}
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
