package com.attendee.attendee.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class SampleMail {

	private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final String ALIDM_SMTP_PORT = "25";//or "80"
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    
    	final Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.host", ALIDM_SMTP_HOST);
//        props.put("mail.smtp.port", ALIDM_SMTP_PORT);
    	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	props.put("mail.smtp.socketFactory.port", "465");
    	props.put("mail.smtp.port", "465");
        props.put("mail.user", "no-reply@attendees.today");
        props.put("mail.password", "ADmin12345");
        Authenticator authenticator = new Authenticator() {
        
	    	@Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            String userName = props.getProperty("mail.user");
	            String password = props.getProperty("mail.password");
	            return new PasswordAuthentication(userName, password);
	        }
        };

        Session mailSession = Session.getInstance(props, authenticator);
        MimeMessage message = new MimeMessage(mailSession);
        
        try {
	        InternetAddress from = new InternetAddress("no-reply@attendees.today", "sender name(AttendeeApp)");
	        message.setFrom(from);
	        	        
	        InternetAddress to = new InternetAddress("ajiprayitno0296@gmail.com");
	        message.setRecipient(MimeMessage.RecipientType.TO, to);
	        message.setSubject("Test mail");
	        message.setContent("Test HTML mail", "text/html;charset=UTF-8");
	        Transport.send(message);
        }
        catch (MessagingException e) {
            String err = e.getMessage();
            System.out.println(err);
        }
    }
}
