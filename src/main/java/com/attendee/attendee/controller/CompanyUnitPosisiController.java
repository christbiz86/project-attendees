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
import com.attendee.attendee.model.CompanyUnitPosisi;
import com.attendee.attendee.service.CompanyUnitPosisiService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class CompanyUnitPosisiController {

	@Autowired
	private CompanyUnitPosisiService companyUnitPosisiService;
	
	@RequestMapping(value = "/companyunitposisi", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveByFilter(@RequestBody CompanyUnitPosisi companyUnitPosisi) throws ValidationException
	{
		 try 
		 {
			 List<CompanyUnitPosisi> posisiList=companyUnitPosisiService.findByFilter(companyUnitPosisi);

			 return ResponseEntity.ok(posisiList);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}

	
	@RequestMapping(value = "/companyunitposisi", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody CompanyUnitPosisi companyUnitPosisi) throws ValidationException{
		try {
			companyUnitPosisiService.insert(companyUnitPosisi);
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
	
	@RequestMapping(value =  "/companyunitposisi", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody CompanyUnitPosisi companyUnitPosisi) throws Exception
	{
		 try 
		 {
			 companyUnitPosisiService.update(companyUnitPosisi);
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
