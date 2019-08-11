package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.PojoUser;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UserController {

	@Autowired
	private UserService userService;
		
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveByFilter(@RequestBody User user) throws ValidationException
	{
		 try 
		 {
			 List<User> userList=userService.findByFilter(user);

			 return ResponseEntity.ok(userList);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}

	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody User user) throws ValidationException{
		try {
//			user.setPassword(encoder.encode(user.getPassword()));
<<<<<<< HEAD
			user.setCreatedBy(userService.findById(
					((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
=======
//			user.setCreatedBy(userService.findById(
//					((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
>>>>>>> a61a5452fffee3ba7be04723c7b2b914fa2afb9f

			userService.save(user);
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
	
	@RequestMapping(value =  "/user", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody User user) throws Exception
	{
		 try 
		 {
			 user.setUpdatedBy(userService.findById(
						((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
			 userService.update(user);
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
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
<<<<<<< HEAD
	@PreAuthorize("hasRole('Super Admin')")
=======
//	@PreAuthorize("hasRole('Super Admin')")
>>>>>>> a61a5452fffee3ba7be04723c7b2b914fa2afb9f
	public ResponseEntity<?> retrieveAll() throws ValidationException
	{
		 try 
		 {
			 List<User> userList=userService.findAll();

			 return ResponseEntity.ok(userList);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<?> submitWithCompanyUnitPosisi(@RequestBody PojoUser user) throws ValidationException{
		try {
//			user.getUser().setPassword(encoder.encode(user.getUser().getPassword()));
<<<<<<< HEAD
			user.getUser().setCreatedBy(userService.findById(
					((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
=======
//			user.getUser().setCreatedBy(userService.findById(
//					((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
>>>>>>> a61a5452fffee3ba7be04723c7b2b914fa2afb9f
			userService.saveWithCompanyUnitPosisi(user);
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
	
	@RequestMapping(value = "/userLoggedin", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveUserLogedIn() throws ValidationException
	{
		 try 
		 {
			 UserPrinciple user=(UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			 return ResponseEntity.ok(user);
		 }
		 
		 catch(Exception ex) 
		 {
			 MessageResponse mg = new MessageResponse("Retrieve Failed" );
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		 }	
	}


}
