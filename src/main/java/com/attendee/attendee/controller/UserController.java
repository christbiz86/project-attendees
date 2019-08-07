package com.attendee.attendee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.User;
import com.attendee.attendee.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveByFilter(@RequestBody User user) throws ValidationException {
		try {
			List<User> userList = userService.findByFilter(user);
			return ResponseEntity.ok(userList);
		}

		catch (Exception ex) {
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> submit(@RequestBody User user) throws ValidationException {
		try {
			userService.save(user);
			MessageResponse mg = new MessageResponse("Success submit");

			return ResponseEntity.ok(mg);

		} catch (ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());

		} catch (Exception e) {
			System.out.println(e);

			MessageResponse mg = new MessageResponse("Failed submit");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody User user) throws Exception {
		try {
			userService.update(user);
			MessageResponse mg = new MessageResponse("Success update");
			return ResponseEntity.ok(mg);
		}

		catch (ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());

		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Failed update");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAll() throws ValidationException {
		try {
			List<User> userList = userService.findAll();
			return ResponseEntity.ok(userList);
		}

		catch (Exception ex) {
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
}
