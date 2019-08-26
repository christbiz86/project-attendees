package com.attendee.attendee;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.attendee.attendee.storage.StorageProperties;

@SpringBootApplication 
@EnableAutoConfiguration
@ComponentScan(basePackages= "com.attendee.attendee")
@EnableTransactionManagement
@EntityScan(basePackages= {"com.attendee.attendee."})
@EnableConfigurationProperties(StorageProperties.class)
public class AttendeeApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		
		new File("upload-dir").mkdir();

		 
		SpringApplication.run(AttendeeApplication.class, args);
	}

}
