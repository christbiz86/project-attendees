package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.AttendeeDao;
import com.attendee.attendee.dao.UserShiftProjectDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Attendee;
import com.attendee.attendee.model.PojoAbsen;
import com.attendee.attendee.model.UserCompany;
import com.attendee.attendee.model.UserShiftProject;

@Service
public class AttendeeService {

	@Autowired
	private AttendeeDao attendeeDao;
	
	@Autowired
	private UserShiftProjectDao uspDao;
	
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
	
	public List<Attendee> findByFilter(Attendee attendee) throws ValidationException {
		return attendeeDao.findByFilter(attendee);
	}
	
	private String valLokasi(List<String> listKode, UUID user) throws ValidationException {
		UserShiftProject usp = uspDao.findByUser(user);
		for (String code : listKode) {
			if(code.equals(usp.getShiftProject().getProject().getGlobalCode())) {
				return usp.getShiftProject().getProject().getLokasi();
			}
		}
		throw new ValidationException("Harap Absen di Lokasi Projek");
	}
	
	public void saveAbsen(PojoAbsen absen,UserCompany uc) throws ValidationException {
		String lokasi = valLokasi(absen.getKode(), uc.getId());
		
		Attendee temp=attendeeDao.findByUserAndKeterangan(uc.getIdUser().getId(), 
				"masuk", new Timestamp(System.currentTimeMillis()));
		if(temp.getId()==null) {
			Attendee attendee=new Attendee();
			UserShiftProject usp=new UserShiftProject();
			usp.setUserCompany(uc);

			attendee.setIdUserShiftProject(usp);
			usp=uspService.findByUser(attendee.getIdUserShiftProject());
		
			attendee.setIdUserShiftProject(usp);
			attendee.setMasuk(new Timestamp(System.currentTimeMillis()));
			attendee.setLokasiMasuk(lokasi);
			attendee.setKeterangan("masuk");
			
			save(attendee);
			
		}else {
			
			temp.setLokasiPulang(lokasi);
			temp.setPulang(new Timestamp(System.currentTimeMillis()));
			temp.setKeterangan("pulang");
			
			update(temp);
			
		}
	}
}