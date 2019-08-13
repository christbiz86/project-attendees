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
import com.attendee.attendee.model.UserCompany;
import com.attendee.attendee.service.UserCompanyService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UserCompanyController {

	@Autowired
	private UserCompanyService userCompanyService;
	
	@RequestMapping(value = "/usercompany/filter", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByFilter(@RequestBody UserCompany userCompany) throws ValidationException
	{
		 try 
		 {
			 List<UserCompany> list=userCompanyService.findByFilter(userCompany);

			 return ResponseEntity.ok(list);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}
	
	@RequestMapping(value = "/usercompany", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() throws ValidationException
	{
		 try 
		 {
			 List<UserCompany> list=userCompanyService.findAll();

			 return ResponseEntity.ok(list);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}

	
	@RequestMapping(value = "/usercompany", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody UserCompany userCompany) throws ValidationException{
		try {
			userCompanyService.save(userCompany);
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
	
	@RequestMapping(value =  "/usercompany", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody UserCompany userCompany) throws Exception
	{
		 try 
		 {
			 userCompanyService.update(userCompany);
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
