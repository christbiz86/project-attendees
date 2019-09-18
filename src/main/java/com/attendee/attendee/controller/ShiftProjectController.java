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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.model.Shift;
import com.attendee.attendee.model.ShiftProject;
import com.attendee.attendee.service.ShiftProjectService;
import com.attendee.attendee.service.ShiftService;
import com.attendee.attendee.service.StatusService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class ShiftProjectController {
	@Autowired
	private ShiftProjectService shiftProjectService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private StatusService stServ;
	
	@GetMapping(value = "shift-project")
	public @ResponseBody List<ShiftProject> getAllShiftProject(){
		List<ShiftProject> shiftProjectList = shiftProjectService.findAll();
		return shiftProjectList;
	}
	
	@PostMapping(value = "shift-project")
	public ResponseEntity<?> insertShiftProject(@RequestBody ShiftProject sp) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
//		try {
//			shiftProjectService.save(shiftProject);
//			MessageResponse mg = new MessageResponse("Insert Success");
//			messagesSuccess.add(mg);
//		} catch (Exception e) {
//			MessageResponse mg = new MessageResponse("Insert Failed");
//			messagesException.add(mg);
//		}
		
		try {			
			Shift shift = shiftService.findByNonBk(sp.getShift().getMasuk(), sp.getShift().getPulang(), sp.getShift().getStatus().getStatus());
			System.out.println(sp.getShift().getMasuk());
			System.out.println(sp.getShift().getPulang());
			System.out.println(sp.getShift().getStatus().getStatus());
			if(shift.getKode() == null) {
				shift = new Shift();
				
				shift.setMasuk(sp.getShift().getMasuk());
				shift.setPulang(sp.getShift().getPulang());
				shift.setStatus(stServ.findByStatus(sp.getShift().getStatus().getStatus()));
				
				shiftService.save(shift);
			} 
			
			try {
				System.out.println("masuk "+sp.getShift().getMasuk());
				System.out.println("pulang "+sp.getShift().getPulang());
				System.out.println("project "+sp.getProject().getId());
				
				ShiftProject findSp = shiftProjectService.findByShiftProject(shiftService.findByNonBk(sp.getShift().getMasuk(), sp.getShift().getPulang(), sp.getShift().getStatus().getStatus()), sp.getProject());
				
				if(findSp.getId() == null) {
					ShiftProject newSp = new ShiftProject();
					
					System.out.println(shift.getId());
					newSp.setShift(shift);
					newSp.setProject(sp.getProject());
//					System.out.println(newSp.getShift().getId());
//					System.out.println(newSp.getProject().getId());
					
					shiftProjectService.save(newSp);
					
					MessageResponse mg = new MessageResponse("Insert Success with clock in " +sp.getShift().getMasuk()+ " and clock out " +sp.getShift().getPulang());
					messagesSuccess.add(mg);
					
				} else {
					MessageResponse mg = new MessageResponse("Data shift project sudah ada!");
					messagesSuccess.add(mg);
				}
				
			} catch (Exception e) {
				MessageResponse mg = new MessageResponse("Insert shift project failed");
				messagesException.add(mg);
			}
			
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Insert shift failed");
			messagesException.add(mg);
		}
		
		if(messagesFailed.size() > 0 && messagesSuccess.size() > 0) {
			allMessages.add(messagesSuccess);
			allMessages.add(messagesFailed);
			return ResponseEntity.ok(allMessages);
		}
		
		if(messagesSuccess.size() > 0) {
			return ResponseEntity.ok(messagesSuccess);
			
		}
		
		if(messagesFailed.size() > 0 ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesFailed);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
	
	@PutMapping(value = "shift-project")
	public ResponseEntity<?> submitUpdate(@RequestBody ShiftProject shiftProject) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			shiftProjectService.update(shiftProject);
			MessageResponse mg = new MessageResponse("Update Success");
			messagesSuccess.add(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Update Failed");
			messagesException.add(mg);
		}
		
		if(messagesSuccess.size() > 0) {
			return ResponseEntity.ok(messagesSuccess);
			
		}
		
		if(messagesFailed.size() > 0 ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesFailed);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
}
