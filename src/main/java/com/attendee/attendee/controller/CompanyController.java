package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Company;
import com.attendee.attendee.model.CompanyUnitPosisi;
import com.attendee.attendee.model.RegistrationForm;
import com.attendee.attendee.model.Status;
import com.attendee.attendee.model.TipeUser;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserCompany;
import com.attendee.attendee.service.CompanyService;
import com.attendee.attendee.service.CompanyUnitPosisiService;
import com.attendee.attendee.service.StatusService;
import com.attendee.attendee.service.TipeUserService;
import com.attendee.attendee.service.UserCompanyService;
import com.attendee.attendee.service.UserService;

@CrossOrigin(origins = "*")
@Controller
@RestController
public class CompanyController {
	@Autowired
	private CompanyService comServ;
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private StatusService stServ;
	
	@Autowired
	private UserCompanyService ucServ;
	
	@Autowired
	private TipeUserService tuServ;
	
	@Autowired
	private CompanyUnitPosisiService cupServ;
	
	@PostMapping(value = "/company")
	public ResponseEntity<?> save(@RequestBody RegistrationForm regForm) throws Exception {
		List<MessageResponse> messagesSuccess = new ArrayList();
		List<MessageResponse> messagesExist = new ArrayList();
		List<MessageResponse> messagesException = new ArrayList();
	    List allMessages =  new ArrayList();
	    
	    Company company = new Company();	    
	    User user = new User();
	    
	    Status sc = stServ.findByStatus(regForm.getStatusCompany());
	    Status su = stServ.findByStatus(regForm.getStatusUser());
	    
	    //set company
	    company.setKode(regForm.getKodeCompany());
	    company.setNama(regForm.getNamaCompany());
	    company.setJatahCuti(regForm.getJatahCuti());
	    company.setToleransiKeterlambatan(regForm.getToleransiKeterlambatan());
	    company.setIdStatus(sc);
	    
	    //set user
	    user.setKode(regForm.getKodeUser());
	    user.setNama(regForm.getNamaUser());
	    user.setAlamat(regForm.getAlamat());
	    user.setEmail(regForm.getEmail());
	    user.setTglLahir(regForm.getTglLahir());
	    user.setTelp(regForm.getTelp());
	    user.setFoto(regForm.getFoto());
	    user.setIdStatus(su);
	    
		try {
			comServ.insert(company);
			userServ.save(user);
			
			CompanyUnitPosisi cup = new CompanyUnitPosisi(); 
			
			Company kc = comServ.findByBk(regForm.getKodeCompany());
			
			//set company unit posisi
			cup.setIdCompany(kc);			
			
			try {
				cupServ.insert(cup);
				
				UserCompany uc = new UserCompany();
				
				User uBk = new User(); 
				uBk.setKode(regForm.getKodeUser());
				uBk=userServ.findByBk(uBk);
				TipeUser tuTipe = tuServ.findByTipe("Super Admin");
				
				//set user company
				uc.setIdUser(uBk);
				uc.setIdTipeUser(tuTipe);
				
				try {
					ucServ.save(uc);
					MessageResponse mr = new MessageResponse("Insert success with company name "+company.getNama()+ " and user name "+user.getNama());
					messagesSuccess.add(mr);
					return ResponseEntity.status(HttpStatus.CREATED).body(mr);
				} catch (Exception e) {
					System.out.println(e);
					MessageResponse mr = new MessageResponse("Insert company unit posisi failed!");
					messagesException.add(mr);
				}
				
			} catch (Exception e) {
				System.out.println(e);
				MessageResponse mr = new MessageResponse("Insert user company failed!");
				messagesException.add(mr);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			MessageResponse mr = new MessageResponse("Insert all failed!");
			messagesException.add(mr);
		}
		
		if (messagesSuccess.size() > 0 && messagesExist.size() > 0) {
			allMessages.add(messagesSuccess);
			allMessages.add(messagesExist);
			return ResponseEntity.ok(allMessages);
		}

		if (messagesSuccess.size() > 0) {
			return ResponseEntity.ok(messagesSuccess);
		}

		if (messagesExist.size() > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesExist);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
	
	@PutMapping(value = "/company")
	public ResponseEntity<?> update(@RequestBody Company company) throws Exception {
		try {
			comServ.update(company);
			
			MessageResponse mr = new MessageResponse("Update success company code "+company.getKode()+" and name "+company.getNama());
			return ResponseEntity.ok(mr);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Update failed!");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
	
	@GetMapping(value = "/companies")
	public ResponseEntity<?> findAll() throws Exception {
		try {
			List<Company> list = comServ.findAll();
			return new ResponseEntity<List<Company>>(list, HttpStatus.OK);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Retrieve failed!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
		}
	}
	
	@GetMapping(value = "/company")
	public ResponseEntity<?> findByFilter(@RequestBody Company company) throws Exception {
		try {
			Company list = comServ.findByFilter(company);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Retrieve failed!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mr);
		}
	}
		
}
