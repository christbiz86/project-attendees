package com.attendee.attendee;

import java.security.MessageDigest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AttendeeApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AttendeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		String passwd = "user";
		System.out.println("Password is: " + passwd);
		
		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.reset();
		alg.update(passwd.getBytes());
		byte[] digest = alg.digest();
		
		StringBuffer hashedpasswd = new StringBuffer();
	    String hx;
	    for (int i=0;i<digest.length;i++){
	        hx =  Integer.toHexString(0xFF & digest[i]);
	        //0x03 is equal to 0x3, but we need 0x03 for our md5sum
	        if(hx.length() == 1){hx = "0" + hx;}
	        hashedpasswd.append(hx);
	    }
	    
	    System.out.println("MD5 version is: " + hashedpasswd.toString() + "<br>");
	}

}
