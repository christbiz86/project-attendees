package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.TipeUser;
import com.attendee.attendee.service.TipeUserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class TipeUserController {

	@Autowired
	private TipeUserService tipeUserService;
	
	@RequestMapping(value = "/tipeuser", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAll() throws ValidationException
	{
		 try 
		 {
			 List<TipeUser> list=tipeUserService.findAll();

			 return ResponseEntity.ok(list);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}
	
	@RequestMapping(value = "/tipeusefilterr", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByFilter(@RequestBody TipeUser tipeUser) throws ValidationException
	{
		 try 
		 {
			 List<TipeUser> list=tipeUserService.findByFilter(tipeUser);

			 return ResponseEntity.ok(list);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}
	
	@RequestMapping(value = "/tipeuser", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody TipeUser tipeUser) throws ValidationException{
		try {
			tipeUserService.save(tipeUser);
			MessageResponse mg  = new MessageResponse("Success submit");
			
			return ResponseEntity.ok(mg);
			
		}
		catch(ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
			
		 }
		catch (Exception e) {
			MessageResponse mg = new MessageResponse("Failed submit" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@RequestMapping(value =  "/tipeuser", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody TipeUser tipeUser) throws Exception
	{
		 try 
		 {
			 tipeUserService.update(tipeUser);
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
