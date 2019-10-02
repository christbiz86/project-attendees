package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.exception.MessageListResponse;
import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.PojoShift;
import com.attendee.attendee.model.Shift;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.service.ShiftProjectService;
import com.attendee.attendee.service.ShiftService;
import com.attendee.attendee.service.StatusService;
import com.attendee.attendee.service.UserCompanyService;
import com.attendee.attendee.service.UserService;
import com.attendee.attendee.service.UserShiftProjectService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class ShiftController {
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private ShiftProjectService spServ;
	
	@Autowired
	private UserShiftProjectService uspServ;
	
	@Autowired
	private StatusService stServ;
	
	@Autowired
	private UserCompanyService ucServ;
	
	@Autowired
	private UserService userService;
		
	@GetMapping(value = "/shift")
	public @ResponseBody List<Shift> getAllShift(){
		List<Shift> shiftList = shiftService.findAll();
		return shiftList;
	}
	
	@GetMapping(value = "/shift/{status}")
	public @ResponseBody List<Shift> getFilterShift(@PathVariable String status) throws Exception {
		List<Shift> shiftList = shiftService.filterShift(status);
		return shiftList;
	}
	
//	public Shift splitShift(PojoShift ps) throws Exception {
//		Shift shiftDB = shiftService.findByNonBk(ps.getMasuk(), ps.getPulang());
//		if(shiftDB.getKode() == null) {
//			Shift shift = new Shift();
//			
//			shift.setMasuk(ps.getMasuk());
//			shift.setPulang(ps.getPulang());
//			
//			shiftService.save(shift);
//			
//			return shift;
//		} else {
//			return shiftDB;
//		}
//	}
//	
//	public ShiftProject splitShiftProject(PojoShift ps) throws ValidationException {
//		Shift shift = shiftService.findByNonBk(ps.getMasuk(), ps.getPulang());
//		ShiftProject sp = new ShiftProject();
//		
//		sp.setShift(shift);
//		sp.setProject(ps.getProject());
//		spServ.save(sp);
//		
//		return sp;
//	}
//	
//	public UserShiftProject splitUserShiftProject(PojoShift ps) throws ValidationException {
//		UserShiftProject usp = new UserShiftProject();
//		System.out.println(shiftService.findByNonBk(ps.getMasuk(), ps.getPulang()).getId());
//		System.out.println(ps.getProject().getId());
//		System.out.println(ps.getUc().getId());
//		System.out.println(stServ.findByStatus(ps.getWorktime().getStatus()).getStatus());
//		
//		ShiftProject sp = spServ.findByShiftProject(shiftService.findByNonBk(ps.getMasuk(), ps.getPulang()), ps.getProject());
//		
////		usp.setUserCompany(ucServ.findByCup(ps.getCup()));
//		usp.setUserCompany(ps.getUc());
//		usp.setShiftProject(sp);
//		usp.setWorktime(stServ.findByStatus(ps.getWorktime().getStatus()));
//		uspServ.save(usp);
//		
//		return usp;
//	}
	
	@PostMapping(value = "/shift")
	public ResponseEntity<?> insertShift(@RequestBody PojoShift ps) throws Exception {		
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
//			splitShift(ps);
			
			try {
//				splitShiftProject(ps);
				
				try {
//					splitUserShiftProject(ps);
					
//					MessageResponse mg = new MessageResponse("Insert Success with clock in " +splitShift(ps).getMasuk()+ " and clock out " +splitShift(ps).getPulang()+ " and worktime " +splitUserShiftProject(ps).getWorktime().getStatus());
//					messagesSuccess.add(mg);
				} catch (Exception e) {
					MessageResponse mg = new MessageResponse("Insert all failed");
					messagesException.add(mg);
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
			
		} else if(messagesFailed.size() > 0 ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesFailed);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messagesException);
		}
	}
	
	@PutMapping(value = "/shift")
	public ResponseEntity<?> updateShift(@RequestBody Shift shift) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
		try {
			shift.setStatus(stServ.findByStatus(shift.getStatus().getStatus()));
			
			shiftService.update(shift);
			MessageResponse mg = new MessageResponse("Update Success With Code Shift " + shift.getKode());
			messagesSuccess.add(mg);
		} catch (InvalidDataException ex) {
			MessageListResponse mg = new MessageListResponse(ex.getMessages());
			messagesFailed.add(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Update Failed");
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
	
	@PatchMapping(value = "/shift/inactive")
	public ResponseEntity<?> patchShift(@RequestBody Shift shift) throws Exception {
		try {
//			shiftService.delete(id);
			shiftService.delete(shift, ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdUser());
			MessageResponse mr = new MessageResponse("Delete success with id "+shift.getId());
			return ResponseEntity.ok(mr);
		} catch (Exception e) {
			MessageResponse mr = new MessageResponse("Delete failed with id " + shift.getId());
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mr);
		}
	}
	
	@PutMapping(value = "shifts")
	public ResponseEntity<?> submitUpdates(@RequestBody HashMap<Integer, Shift> shift) throws Exception {
		try {
			for (Map.Entry<Integer, Shift> entry : shift.entrySet()) {
				Shift s = entry.getValue();
				s.setUpdatedBy(userService.findById(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
				
				shiftService.update(s);
			}
			
			MessageResponse mg = new MessageResponse("Update Success!");
			return ResponseEntity.ok(mg);
		} 
		
		catch (ValidationException val) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(val.getMessage());
		}
		catch (Exception e) {
			MessageResponse mg = new MessageResponse("Update failed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
}
