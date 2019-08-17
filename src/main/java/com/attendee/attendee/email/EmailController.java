package com.attendee.attendee.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Controller
public class EmailController {
	
	@Autowired
	private EmailServiceImpl eService;
	
	@PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody Email email) {
 
		try {
			eService.sendSimpleMessage(email.getTo(), email.getSubject(), email.getText());
			
			MessageResponse mr = new MessageResponse("terkirim!");
			return ResponseEntity.status(HttpStatus.OK).body(mr);

		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("failed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}        
    }
}
