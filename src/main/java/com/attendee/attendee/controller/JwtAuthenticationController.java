//package com.attendee.attendee.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.attendee.attendee.config.JwtRequest;
//import com.attendee.attendee.config.JwtResponse;
//import com.attendee.attendee.exception.MessageResponse;
//import com.attendee.attendee.exception.ValidationException;
//import com.attendee.attendee.model.User;
//import com.attendee.attendee.service.JwtUserDetailsService;
//import com.attendee.attendee.service.TipeUserService;
//import com.attendee.attendee.service.UserService;
//
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@Controller
//@Transactional
//public class JwtAuthenticationController {
//
//	@Autowired
//    AuthenticationManager authenticationManager;
// 
//    @Autowired
//    UserService userService;
// 
//    @Autowired
//    TipeUserService roleService;
// 
//    @Autowired
//    PasswordEncoder encoder;
// 	    
//	@Autowired
//	private JwtUserDetailsService userDetailsService;
//
//	@RequestMapping(value = "/signin", method = RequestMethod.POST)
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authRequest){
//
//		try {
//			String token = userDetailsService.getToken(authRequest.getUsername(), authRequest.getPassword());
//			return ResponseEntity.ok(new JwtResponse(token));
//		}
//		catch (UsernameNotFoundException e) {
//			MessageResponse mg = new MessageResponse("User not found with username: " + authRequest.getUsername());
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mg);
//
//		} 
//		catch (Exception ex) {
//			MessageResponse mg = new MessageResponse("Authentication failed! ");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
//		}
//	}
//	
//
//		@RequestMapping(value = "/signup", method = RequestMethod.POST)
//		@PreAuthorize("hasRole('ADMIN') OR hasRole('SUPERADMIN')")
//	    public ResponseEntity<?> registerUser(@RequestBody User signUpRequest) throws ValidationException {
//	       
//	        // Creating user's account
//			try {
//				signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
//		        userService.save(signUpRequest);
//
//				MessageResponse mg  = new MessageResponse("User registered successfully!");
//				
//				return ResponseEntity.ok(mg);
//				
//			}
//			catch(ValidationException val) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
//				
//			 }
//			catch (Exception e) {
//				 System.out.println(e);
//
//				MessageResponse mg = new MessageResponse("User registered failled" );
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
//			}
//
//	    }
//	}
//
