package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Company;
import com.attendee.attendee.service.CompanyService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value = "/company/{kode}", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveByKode(@PathVariable String kode) throws ValidationException
	{
		 try 
		 {
			 Company company=companyService.findKode(kode);

			 return ResponseEntity.ok(company);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAll() throws ValidationException
	{
		 try 
		 {
			 List<Company> companyList=companyService.findAll();

			 return ResponseEntity.ok(companyList);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}
	
	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody Company company) throws ValidationException{
		try {
			companyService.save(company);
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
	
	@RequestMapping(value =  "/company", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Company company) throws Exception
	{
		 try 
		 {
			 companyService.update(company);
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
