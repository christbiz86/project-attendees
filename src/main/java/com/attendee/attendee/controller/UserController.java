<<<<<<< HEAD
package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.attendee.attendee.email.EmailServiceImpl;
import com.attendee.attendee.email.PasswordGenerator;
import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.PojoUser;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.UserService;
import com.attendee.attendee.storage.StorageService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailServiceImpl eService;
	
	@Autowired
	private PasswordGenerator pwGenerator;

	@Autowired
    	private StorageService storageService;
	
	@RequestMapping(value = "/user/filter", method = RequestMethod.POST)
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

		
	@RequestMapping(value =  "/user", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody User user) throws Exception
	{
		 try 
		 {
			 user.setUpdatedBy(userService.findById(
						((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).getId());
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
	
	
    @PostMapping("/user/save")
    public ResponseEntity<?> saveNewEmployee(@RequestParam("file") MultipartFile file, 
            @RequestParam("user") PojoUser user) throws Exception,ValidationException{
    	
    	try {
    		System.out.println(file.getOriginalFilename());
    		System.out.println(user.getUser().getNama());
    		
    		storageService.store(file,user.getUser().getEmail()+".jpg");
	
    		try {
    			String pass=pwGenerator.generatePassword(user.getUser());
        		user.getUser().setFoto(user.getUser().getEmail()+".jpg");
    			user.getUser().setPassword(pass);
    			user.getUser().setCreatedBy(userService.findById(
    					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    			userService.saveWithCompanyUnitPosisi(user);
    			eService.sendSimpleMessage(user.getUser().getEmail(), "Registration Attendee App Password", ("Your email : "+user.getUser().getEmail()+"\n"
    					+ "password : "+pass+"\n Thank you "));

    			MessageResponse mg  = new MessageResponse("Success submit");
    			
    			return ResponseEntity.ok(mg);
    			
    		}
    		catch(ValidationException val) {
    			storageService.deleteOne(user.getUser().getFoto());
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
    			
    		 }
    		
    	}catch (Exception e) {
    		System.out.println(e);
			storageService.deleteOne(user.getUser().getFoto());
			MessageResponse mg = new MessageResponse("Failed insert" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
        }
    }

}
=======
package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.attendee.attendee.email.EmailServiceImpl;
import com.attendee.attendee.email.PasswordGenerator;
import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.PojoUser;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.UserService;
import com.attendee.attendee.storage.StorageService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailServiceImpl eService;
	
	@Autowired
	private PasswordGenerator pwGenerator;

	@Autowired
    	private StorageService storageService;
	
	@RequestMapping(value = "/user/filter", method = RequestMethod.POST)
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

		
	@RequestMapping(value =  "/user", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody User user) throws Exception
	{
		 try 
		 {
			 user.setUpdatedBy(userService.findById(
						((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).getId());
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
	
	
    @PostMapping("/user/save")
    public ResponseEntity<?> saveNewEmployee(@RequestParam("file") MultipartFile file, 
            @RequestParam("user") PojoUser user) throws Exception,ValidationException{
    	
    	try {
    		System.out.println(file.getOriginalFilename());
    		System.out.println(user.getUser().getNama());
    		
    		storageService.store(file,user.getUser().getEmail());
	
    		try {
    			String pass=pwGenerator.generatePassword(user.getUser());
        		user.getUser().setFoto(user.getUser().getEmail()+".jpg");
    			user.getUser().setPassword(pass);
    			user.getUser().setCreatedBy(userService.findById(
    					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    			userService.saveWithCompanyUnitPosisi(user);
    			eService.sendSimpleMessage(user.getUser().getEmail(), "Registration Attendee App Password", ("Your email : "+user.getUser().getEmail()+"\n"
    					+ "password : "+pass+"\n Thank you "));

    			MessageResponse mg  = new MessageResponse("Success submit");
    			
    			return ResponseEntity.ok(mg);
    			
    		}
    		catch(ValidationException val) {
    			storageService.deleteOne(user.getUser().getFoto());
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
    			
    		 }
    		
    	}catch (Exception e) {
    		System.out.println(e);
			storageService.deleteOne(user.getUser().getFoto());
			MessageResponse mg = new MessageResponse("Failed insert" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
        }
    }

}
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
