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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.model.Notification;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.NotificationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/notification"})
@Controller
public class NotificationController {

	@Autowired
	private NotificationService notifService;
	
	@GetMapping
	public ResponseEntity<?> getAll() throws IOException {
		try {
			List<Notification> notification = notifService.findAll();
			return ResponseEntity.ok(notification);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping(value = "/request")
	public ResponseEntity<?> getByFilterRequest(@RequestBody Notification notification) throws IOException {
		try {
			notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().setIdCompany(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			List<Notification> notifList = notifService.findByFilterRequest(notification);
			return ResponseEntity.ok(notifList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping(value = "/approval")
	public ResponseEntity<?> getByFilterNotRequest(@RequestBody Notification notification) throws IOException {
		try {
			notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().setIdCompany(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			List<Notification> notifList = notifService.findByFilterNotRequest(notification);
			return ResponseEntity.ok(notifList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PatchMapping
	public ResponseEntity<?> update(@RequestBody Notification notification) throws IOException{
		try {
			notifService.update(notification);
			return ResponseEntity.ok("Data Notification Berhasil Diubah");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
	