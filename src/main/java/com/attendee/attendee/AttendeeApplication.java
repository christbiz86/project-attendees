<<<<<<< HEAD
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
		
		new File("src/main/resources/upload-dir").mkdir();
		new File("src/main/resources/report").mkdir();
		 
		SpringApplication.run(AttendeeApplication.class, args);
	}

}
=======
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
		
		new File("src/main/resources/upload-dir").mkdir();
		new File("src/main/resources/report").mkdir();
		 
		SpringApplication.run(AttendeeApplication.class, args);
	}

}
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
