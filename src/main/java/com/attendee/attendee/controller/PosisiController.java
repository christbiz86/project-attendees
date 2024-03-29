package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Posisi;
import com.attendee.attendee.model.Status;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.PosisiService;
import com.attendee.attendee.service.StatusService;
import com.attendee.attendee.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class PosisiController {

	@Autowired
	private PosisiService posisiService;
	
	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/posisi/filter", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByFilter(@RequestBody Posisi posisi) throws ValidationException
	{
		 try 
		 {
			 List<Posisi> posisiList=posisiService.findByFilter(posisi);

			 return ResponseEntity.ok(posisiList);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}
	
	@RequestMapping(value = "/posisi", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAll() throws ValidationException
	{
		 try 
		 {
			 List<Posisi> posisiList=posisiService.findAll();

			 return ResponseEntity.ok(posisiList);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}

	
	@RequestMapping(value = "/posisi", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody Posisi posisi) throws ValidationException{
		try {
			posisi.setCreatedBy(userService.findById(
					((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));

			posisiService.save(posisi);
			MessageResponse mg  = new MessageResponse("Success submit");
			
			return ResponseEntity.ok(mg);
			
		}
		catch(ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
			
		 }
		catch (Exception e) {
			System.out.println(e);
			MessageResponse mg = new MessageResponse("Failed submit" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@RequestMapping(value =  "/posisi", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Posisi posisi) throws Exception
	{
		 try 
		 {
			Status stat = statusService.findByStatus(posisi.getIdStatus().getStatus());
			posisi.setIdStatus(stat);
			posisi.setUpdatedBy(userService.findById(
						((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));

			 posisiService.update(posisi);
			 MessageResponse mg = new MessageResponse("Success update");
			 return ResponseEntity.ok(mg);
		 }
		 		 
		 catch(ValidationException val) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
				
		 }
		catch (Exception e) {
			MessageResponse mg = new MessageResponse("Failed update" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
}
