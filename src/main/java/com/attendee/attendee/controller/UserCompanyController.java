package com.attendee.attendee.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.attendee.attendee.email.EmailServiceImpl;
import com.attendee.attendee.email.PasswordGenerator;
import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.PojoUser;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserCompany;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.CloudService;
import com.attendee.attendee.service.UserCompanyService;
import com.attendee.attendee.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UserCompanyController {

	@Autowired
	private UserCompanyService userCompanyService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailServiceImpl eService;

	@Autowired
	private PasswordGenerator pwGenerator;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CloudService cService;

	@RequestMapping(value = "/usercompany/filter/page/{page}/jumlah/{jumlah}", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByFilter(@RequestBody UserCompany userCompany,@PathVariable int page,@PathVariable int jumlah ) throws ValidationException {
		try {
			System.out.println("get user");
			userCompany.getIdCompanyUnitPosisi().setIdCompany(
					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
							.getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			List<UserCompany> list = userCompanyService.findLimit(userCompany,page, jumlah);
			return ResponseEntity.ok(list);
		}

		catch (Exception ex) {
			System.out.println(ex);
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@RequestMapping(value = "/usercompany/filter/{page}/jumlah/{jumlah}", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByLimit(@RequestBody UserCompany userCompany, @PathVariable int page, @PathVariable int jumlah) throws ValidationException {
		try {
			userCompany.getIdCompanyUnitPosisi().setIdCompany(
					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
							.getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			List<UserCompany> list = userCompanyService.findByLimit(userCompany, page, jumlah);
			return ResponseEntity.ok(list);
		}

		catch (Exception ex) {
			System.out.println(ex);
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@RequestMapping(value = "/usercompany/count", method = RequestMethod.POST)
	public ResponseEntity<?> countEmployee(@RequestBody UserCompany userCompany) throws ValidationException {
		try {
			userCompany.getIdCompanyUnitPosisi().setIdCompany(
					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
							.getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			Integer countEmployee = userCompanyService.countEmployee(userCompany);
			return ResponseEntity.ok(countEmployee);
		}

		catch (Exception ex) {
			System.out.println(ex);
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}

	@RequestMapping(value = "/usercompany", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAll() throws ValidationException {
		try {
			List<UserCompany> list = userCompanyService.findAll();
			return ResponseEntity.ok(list);
		}

		catch (Exception ex) {
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}

	@Transactional
	@PostMapping("/usercompany")
	public ResponseEntity<?> saveNewEmployee(@RequestParam("file") MultipartFile file,
			@RequestParam("user") String pojoUser) throws Exception, ValidationException {
		PojoUser user = objectMapper.readValue(pojoUser, PojoUser.class);
		try {
			String pass = pwGenerator.generatePassword(user.getUser());
			user.getUser().setFoto(user.getUser().getKode() + user.getUser().getNama());
			user.getUser().setPassword(pass);
			user.getUser().setCreatedBy(userService.findById(
					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
			userCompanyService.saveWithCompanyUnitPosisi(user);
			eService.sendSimpleMessage(user.getUser().getEmail(), "Registration Attendee App Password",
					("Your email : " + user.getUser().getEmail() + "\n" + "password : " + pass + "\n Thank you "));

			MessageResponse mg = new MessageResponse("Success submit");
			User saveFoto = userService.findUsername(user.getUser().getEmail());
			cService.save(file, saveFoto.getKode() + saveFoto.getNama());
			return ResponseEntity.ok(mg);
			
		} catch (ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
			
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Failed insert");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}

	@RequestMapping(value = "/usercompany", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody UserCompany userComp) throws Exception {
		try {
			userComp.getIdUser().setUpdatedBy(
					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
			userCompanyService.updateWithCompanyUnitPosisi(userComp);
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
}
