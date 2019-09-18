package com.attendee.attendee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.PojoShift;
import com.attendee.attendee.model.Shift;
import com.attendee.attendee.model.ShiftProject;
import com.attendee.attendee.model.UserPrinciple;
import com.attendee.attendee.model.UserShiftProject;
import com.attendee.attendee.service.ShiftProjectService;
import com.attendee.attendee.service.ShiftService;
import com.attendee.attendee.service.StatusService;
import com.attendee.attendee.service.UserShiftProjectService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Controller
public class UserShiftProjectController {
	@Autowired
	private UserShiftProjectService uspService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private StatusService stServ;
	
	@Autowired
	private ShiftProjectService spServ;
	
	@GetMapping(value = "user-shift-project")
	public @ResponseBody List<UserShiftProject> getAllUserShiftProject(){
		List<UserShiftProject> userShiftProjectList = uspService.findAll();
		return userShiftProjectList;
	}
	
//	@GetMapping(value = "user-shift-project")
//	public @ResponseBody List<UserShiftProject> getFilterUserShift(@PathVariable String worktime) throws Exception {
//		List<UserShiftProject> userShiftList = userShiftProjectService.filterUserShift(worktime);
//		return userShiftList;
//	}
	
	@PostMapping(value = "user-shift-project")
	public ResponseEntity<?> insertUserShiftProject(@RequestBody UserShiftProject usp) throws Exception {
		List messagesFailed = new ArrayList();
		List messagesSuccess = new ArrayList();
		List messagesException = new ArrayList();
		List allMessages = new ArrayList();
		
//		try {
//			Shift shift = shiftService.findByNonBk(usp.getShiftProject().getShift().getMasuk(), usp.getShiftProject().getShift().getPulang());
//			if(shift.getKode() == null) {
//				shift = new Shift();
//				
//				shift.setMasuk(usp.getShiftProject().getShift().getMasuk());
//				shift.setPulang(usp.getShiftProject().getShift().getPulang());
//				
//				shiftService.save(shift);
//			}
//			System.out.println("shift id "+shift.getId());
//			
//			try {
//				System.out.println("masuk "+usp.getShiftProject().getShift().getMasuk());
//				System.out.println("pulang "+usp.getShiftProject().getShift().getPulang());
//				System.out.println("project "+usp.getShiftProject().getProject().getId());
//				
//				ShiftProject sp = spServ.findByShiftProject(shiftService.findByNonBk(usp.getShiftProject().getShift().getMasuk(), usp.getShiftProject().getShift().getPulang()), usp.getShiftProject().getProject());
////				System.out.println(sp.getId()); //null???
//				if(sp.getId() == null) {
//					sp = new ShiftProject();
//					
//					sp.setShift(shift);
//					sp.setProject(usp.getShiftProject().getProject());
//					
//					spServ.save(sp);
//				}
//				System.out.println("shift project id "+sp.getId());
//				
//				try {
//					System.out.println("user company "+usp.getUserCompany().getId());
//					System.out.println("worktime "+usp.getWorktime().getStatus());
//					
//					usp.setUserCompany(usp.getUserCompany());
//					usp.setShiftProject(sp);
//					usp.setWorktime(stServ.findByStatus(usp.getWorktime().getStatus()));
//					
//					uspService.save(usp);
//					
//					MessageResponse mg = new MessageResponse("Insert Success with clock in " +usp.getShiftProject().getShift().getMasuk()+ " and clock out " +usp.getShiftProject().getShift().getPulang()+ " and worktime " +usp.getWorktime().getStatus());
//					messagesSuccess.add(mg);
//					
//				} catch (Exception e) {
//					MessageResponse mg = new MessageResponse("Insert all failed");
//					messagesException.add(mg);
//				}
//				
//			} catch (Exception e) {
//				MessageResponse mg = new MessageResponse("Insert shift project failed");
//				messagesException.add(mg);
//			}
//			
//		} catch (Exception e) {
//			MessageResponse mg = new MessageResponse("Insert shift failed");
//			messagesException.add(mg);
//		}
		
		try {
			usp.setWorktime(stServ.findByStatus(usp.getWorktime().getStatus()));
			
			uspService.save(usp);
			
			MessageResponse mg = new MessageResponse("Insert Success with shift project " +usp.getShiftProject().getId()+ " and name " +usp.getUserCompany().getIdUser().getNama()+ " and worktime " +usp.getWorktime().getStatus());
			messagesSuccess.add(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Insert failed");
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
	
	@DeleteMapping(value = "user-shift-project/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id) throws Exception {
		try {
			uspService.delete(id);
			MessageResponse mg = new MessageResponse("Delete Success");
			return ResponseEntity.ok(mg);
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Delete Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
	
	@RequestMapping(value = "/shift/filter", method = RequestMethod.POST)
	public ResponseEntity<?> retrieveByFilter(@RequestBody UserShiftProject usp) throws ValidationException {
		try {
			usp.getUserCompany().getIdCompanyUnitPosisi().setIdCompany(
					((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
							.getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
			List<UserShiftProject> list = uspService.findByFilter(usp);

			return ResponseEntity.ok(list);
		}

		catch (Exception ex) {
			MessageResponse mg = new MessageResponse("Retrieve Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
	}
}
