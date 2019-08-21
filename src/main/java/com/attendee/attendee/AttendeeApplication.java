package com.attendee.attendee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication 
@EnableAutoConfiguration
@ComponentScan(basePackages= "com.attendee.attendee")
@EnableTransactionManagement
@EntityScan(basePackages= {"com.attendee.attendee."})
public class AttendeeApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(AttendeeApplication.class, args);
	}

}