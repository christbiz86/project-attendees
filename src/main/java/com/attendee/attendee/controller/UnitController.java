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
import com.attendee.attendee.model.Unit;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.UnitService;
import com.attendee.attendee.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UnitController {
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private UserService userService;

	
	@RequestMapping(value = "/unit", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAll() throws ValidationException
	{
		 try 
		 {
			 List<Unit> unitList=unitService.findAll();

			 return ResponseEntity.ok(unitList);
		 }
		 
		 catch(Exception ex) 
		 {
			 System.out.println(ex);
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}

	@RequestMapping(value = "/unitfilter", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByFilter(@RequestBody Unit unit) throws ValidationException
	{
		 try 
		 {
			 List<Unit> unitList=unitService.findByFilter(unit);

			 return ResponseEntity.ok(unitList);
		 }
		 
		 catch(Exception ex) 
		 {
			 System.out.println(ex);
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);	 
		 }
	}
	
	@RequestMapping(value = "/unit", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody Unit unit) throws ValidationException{
		try {
			
			unit.setCreatedBy(userService.findById(
					((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));

			unitService.save(unit);
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
	
	@RequestMapping(value =  "/unit", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Unit unit) throws Exception
	{
		 try 
		 {
			unit.setCreatedBy(userService.findById(
						((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));

			 unitService.update(unit);
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
