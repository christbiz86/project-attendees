package com.attendee.attendee.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.model.Request;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.RequestService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/request"})
@Controller
public class RequestController {
	@Autowired
	private RequestService aprServ;
	
//	static class ApprovalAndUser {
//	    public List<Request> approvals;
//	    public User user;
//	}
	
//	@GetMapping
//	public ResponseEntity<?> getAll() throws IOException {
//		try {
//			ApprovalAndUser aau = new ApprovalAndUser();
//			aau.approvals = aprServ.findAll();
//			aau.user = aau.approvals.get(0).getUser();
//			return ResponseEntity.ok(aau);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		}
//	}
	
	@GetMapping
	public ResponseEntity<?> getAll() throws IOException {
		try {
			List<Request> request = aprServ.findAll();
			return ResponseEntity.ok(request);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/{status}")
	public ResponseEntity<?> getByStatus(@PathVariable String status) throws IOException {
		try {
			List<Request> request = aprServ.findByStatus(status);
			return ResponseEntity.ok(request);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> pengajuan(@RequestBody Request request) throws IOException{
		try {
			aprServ.insert(request);
			return ResponseEntity.status(HttpStatus.CREATED).body("Data Request Berhasil Disimpan");
		}catch (InvalidDataException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessages());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PatchMapping(value = "/{status}")
	public ResponseEntity<?> persetujuan(@RequestBody Request request, @PathVariable String status) throws IOException{
		try {
			//user dari UserPrinciple (belum)
			aprServ.proses(request, ((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdUser(), status);
			return ResponseEntity.ok("Data Request Berhasil Diubah");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
