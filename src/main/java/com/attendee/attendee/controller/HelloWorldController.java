package com.attendee.attendee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class HelloWorldController {
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

}
