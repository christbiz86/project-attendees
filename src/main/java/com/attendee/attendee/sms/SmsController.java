package com.attendee.attendee.sms;

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
public class SmsController {
	
	@Autowired
	private SmsService smsService;
	
	@PostMapping("/sms")
    public ResponseEntity<?> sendSms(@RequestBody Sms sms) {
 
        try {
        	smsService.sendSms(sms);
            
			MessageResponse mr = new MessageResponse("terkirim");
			return ResponseEntity.status(HttpStatus.OK).body(mr);
			
		} catch (Exception e) {
			
			MessageResponse mr = new MessageResponse("gagal");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
    }

}
