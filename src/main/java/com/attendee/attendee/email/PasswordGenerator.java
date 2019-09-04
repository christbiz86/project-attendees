package com.attendee.attendee.email;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.attendee.attendee.model.User;

@Service
public class PasswordGenerator {
	
	
	public String generatePassword(User user) throws NoSuchAlgorithmException {
		String password=user.getEmail();
		Random RANDOM = new SecureRandom();
	    String letters = "1234567890";
	    String pw = "";
	    for (int i=0; i<5; i++) {
	        int index = (int)(RANDOM.nextDouble()*letters.length());
	        pw += letters.substring(index, index+1);
	    }
	    
	    password = (password.substring(0,3).toLowerCase()+pw).toString();
	    System.out.println(password);
	    
	    return password;
		      
	}


}
