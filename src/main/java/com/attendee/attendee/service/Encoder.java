package com.attendee.attendee.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class Encoder {
	
	public String encode(String password) throws NoSuchAlgorithmException {
		MessageDigest alg = MessageDigest.getInstance("MD5");
	    alg.reset(); 
	    alg.update(password.getBytes());
	    byte[] digest = alg.digest();
	
	    StringBuffer hashedpwd = new StringBuffer();
	    String hx;
	    for (int i=0;i<digest.length;i++){
	        hx =  Integer.toHexString(0xFF & digest[i]);
	        //0x03 is equal to 0x3, but we need 0x03 for our md5sum
	        if(hx.length() == 1){hx = "0" + hx;}
	        hashedpwd.append(hx);
	    }
	    
	    return hashedpwd.toString();
	}

}
