package com.attendee.attendee.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.model.Approval;
import com.attendee.attendee.service.ApprovalService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/approval"})
@Controller
public class ApprovalController {
	@Autowired
	private ApprovalService aprServ;
	
	@GetMapping
	public ResponseEntity<?> getAll() throws IOException {
		try {
			List<Approval> approval = aprServ.findAll();
			return ResponseEntity.ok(approval);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody Approval approval){
		try {
			aprServ.insert(approval);
			return ResponseEntity.ok("Data Approval Berhasil Disimpan");
		}catch (InvalidDataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessages());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
