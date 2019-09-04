package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.AttendeeDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Absen;
import com.attendee.attendee.model.Attendee;
import com.attendee.attendee.model.UserCompany;
import com.attendee.attendee.model.UserShiftProject;

@Service
public class AttendeeService {

	@Autowired
	private AttendeeDao attendeeDao;
	
	@Autowired
	private UserShiftProjectService uspService;
	
	private void valIdExist(UUID id)throws ValidationException{
		
		if(!attendeeDao.isIdExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	private void valIdNotNull(Attendee attendee)throws ValidationException {
		
		if(attendee.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	private void valNonBk(Attendee attendee)throws ValidationException{
		
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(attendee.getKeterangan()==null) {
			sb.append("keterangan tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	private void valBkNotExist(Attendee attendee)throws ValidationException{
		if(attendeeDao.isBkExist(attendee)) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	private void valBkNotChange(Attendee attendee)throws ValidationException{
		Attendee at=findById(attendee.getId());
		
		if(!attendee.getIdUserShiftProject().getId().equals(at.getIdUserShiftProject().getId()) || !attendee.getMasuk().equals(at.getMasuk())) {

			throw new ValidationException("user shit project atau jam masuk tidak boleh berubah");
		}
	}
	
	private void valBkNotNull(Attendee attendee) throws ValidationException{
		
		if(attendee.getIdUserShiftProject().getId()==null || attendee.getMasuk()==null) {

			throw new ValidationException("user shit project atau jam masuk tidak boleh kosong");
		}
	}
		
	public void save(Attendee attendee)throws ValidationException{
		
		valBkNotNull(attendee);
		valBkNotExist(attendee);
		valNonBk(attendee);
		attendeeDao.save(attendee);
	}
	
	public void update(Attendee attendee)throws ValidationException{
		
		valIdNotNull(attendee);
		valIdExist(attendee.getId());
		valBkNotNull(attendee);
		valBkNotChange(attendee);
		valNonBk(attendee);
		attendeeDao.save(attendee);
	}
	
	public Attendee findById(UUID id)throws ValidationException{

		return attendeeDao.findById(id);
	}
	
	public Attendee findByBk(Attendee attendee) {

		return attendeeDao.findByBk(attendee.getIdUserShiftProject().getId(),attendee.getMasuk());
	}
	
	public List<Attendee> findAll( )throws ValidationException{
		return attendeeDao.findAll();
	}
	
	public void saveAbsen(Absen absen,UserCompany uc) throws ValidationException {
		
		Attendee temp=attendeeDao.findByUserAndTime(uc.getIdUser().getId());
		
		if(temp.getId()==null) {
			Attendee attendee=new Attendee();
			UserShiftProject usp=new UserShiftProject();
			usp.setUserCompany(uc);

			attendee.setIdUserShiftProject(usp);
			usp=uspService.findByUser(attendee.getIdUserShiftProject());
		
			attendee.setIdUserShiftProject(usp);
			attendee.setMasuk(absen.getJam());
			attendee.setLokasiMasuk(absen.getLokasi());
			attendee.setKeterangan("masuk");
			
			save(attendee);
			
		}else {
			
			temp.setLokasiPulang(absen.getLokasi());
			temp.setPulang(absen.getJam());
			temp.setKeterangan("pulang");
			
			update(temp);
			
		}
	}
}
