package com.attendee.attendee.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.security.jwt.JwtProvider;
import com.attendee.attendee.security.jwt.JwtResponse;
import com.attendee.attendee.service.Encoder;
import com.attendee.attendee.service.UserService;

 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Controller
public class AuthRestAPIs {
 
    @Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private JwtProvider jwtProvider;
 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody User loginRequest) {
    	System.out.println(loginRequest.getEmail());
    	System.out.println(loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setToken(new JwtResponse(jwt));
        return ResponseEntity.ok(user);        
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
    
}
