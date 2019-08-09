package com.attendee.attendee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.security.jwt.JwtProvider;
import com.attendee.attendee.security.jwt.JwtResponse;
import com.attendee.attendee.service.UserService;

 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
 
    @Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private UserService userRepository;
 
 
    @Autowired
    private  PasswordEncoder encoder;
 
    @Autowired
    private JwtProvider jwtProvider;
 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody User loginRequest) {
 
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
 
        String jwt = jwtProvider.generateJwtToken(authentication);
        System.out.println(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isAccountNonExpired());
        System.out.println(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities());
  
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
 
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpRequest) {
    	try {
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
	        
			userRepository.saveWithTipeUser(signUpRequest);
//			userRepository.save(signUpRequest);
//	        
//	        UserCompany userCompany=new UserCompany();
//	        TipeUser tu=tuService.findName("ROLE_SUPERADMIN");
//	        userCompany.setIdUser(userRepository.findByBk(signUpRequest));
//	        userCompany.setIdTipeUser(tu);
//	        userCompanyRepository.save(userCompany);
			MessageResponse mg  = new MessageResponse("User registered successfully!");
			
			return ResponseEntity.ok(mg);
			
		}
		catch(ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
			
		 }
		catch (Exception e) {
			 System.out.println(e);

			MessageResponse mg = new MessageResponse("User registered failled" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
    }

    @GetMapping(value="/logout")
    public ResponseEntity<?> logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
        	auth.setAuthenticated(false);
        	System.out.println(auth);
        	new SecurityContextLogoutHandler().setClearAuthentication(true);
        }
		MessageResponse mg = new MessageResponse("Logged Out" );
        return ResponseEntity.ok(mg);
    }
    
    @GetMapping("/coba")
    public ResponseEntity<?> coba() {
    	try {
			return ResponseEntity.ok(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
			
		}
		catch (Exception e) {
			MessageResponse mg = new MessageResponse("error" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
    }

}