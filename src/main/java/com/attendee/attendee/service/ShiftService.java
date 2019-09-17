package com.attendee.attendee.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ShiftDao;
import com.attendee.attendee.dao.StatusDao;
import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.model.Shift;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;

@Service
public class ShiftService {
	@Autowired 
	private ShiftDao shiftDao;
	
	@Autowired
	private StatusDao stDao;
	
	public void valIdNotExist(UUID id) throws ValidationException {
		if(shiftDao.isExist(id)) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valIdExist(UUID id) throws ValidationException {
		if(!shiftDao.isExist(id)) {
			throw new ValidationException("Data Tidak Ada");
		}
	}
	
	public void valIdNotNull(Shift shift)throws ValidationException {	
		if(shift.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(Shift shift) throws InvalidDataException {
		List<String> listErr = new ArrayList<String>();
		if(shift.getKode() == null) {
			listErr.add("Kode Shift Tidak Boleh Kosong");
		}
		
		if(shift.getMasuk() == null) {
			listErr.add("Jam Masuk Tidak Boleh Kosong ");
		}
		
		if(shift.getPulang() == null) {
			listErr.add("Jam Pulang Tidak Boleh Kosong");
		}
		
		if(shift.getStatus() == null) {
			listErr.add("Status Shift Tidak Boleh Kosong");
		}
		
		if(shift.getCreatedAt() == null) {
			listErr.add("Tanggal Pembuatan Shift Tidak Boleh Kosong");
		}
		
		if(shift.getCreatedBy() == null) {
			listErr.add("Pembuat Shift Tidak Boleh Kosong");
		}
		
		if(!listErr.isEmpty()) {
			throw new InvalidDataException(listErr);
		}
	}
	
	public void valBkNotExist(Shift shift) throws ValidationException {
		if(shiftDao.isBkExist(shift.getKode())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valBkNotChange(Shift shift) throws ValidationException {
		String s = findById(shift.getId()).getKode();
		if(!shift.getKode().toString().equals(s.toString())) {
			throw new ValidationException("Kode Shift Tidak Boleh Diubah");
		}
	}
	
	public void valBkNotNull(Shift shift) throws ValidationException {
		if(shift.getKode() == null) {
			throw new ValidationException("Kode Shift Tidak Boleh Kosong");
		}
	}
	
	public void save(Shift shift) throws Exception {
		shift.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		shift.setStatus(stDao.findByStatus("Active"));
		shift.setCreatedBy(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdUser());
		shift.setKode(kodeShift());
		valBkNotNull(shift);
		valBkNotExist(shift);
		valNonBk(shift);
		shiftDao.save(shift);
	}
	
	public void update(Shift shift) throws Exception {
		System.out.println(shift.getStatus().getStatus());
		System.out.println(shift.getId());
		shift.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//		shift.setStatus(stDao.findByStatus("Inactive"));
		shift.setUpdatedBy(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdUser());
		valIdNotNull(shift);
		valIdExist(shift.getId());
		valBkNotNull(shift);
		valBkNotChange(shift);
		valNonBk(shift);
		shiftDao.save(shift);
	}
	
//	public void delete(UUID id) throws ValidationException {
//		valIdExist(id);
//		shiftDao.delete(id);
//	}
	
	public Shift findById(UUID id) throws ValidationException {
		return shiftDao.findById(id);
	}
	
	public Shift findByBk(String kode) {
		return shiftDao.findByBk(kode);
	}
	
	public List<Shift> findAll() throws ValidationException {
		return shiftDao.findAll();
	}
	
	public List<Shift> filterShift(String status) throws Exception {
		List<Shift> list = shiftDao.filterShift(status);
		if(list.size() == 0) {
			throw new ValidationException("Data Tidak Ditemukan");
		} else {
			return shiftDao.filterShift(status);
		}
		
	}
	
	public String kodeShift() {
		return "SH"+shiftDao.countRows();
	}
	
	public void delete(Shift shift, User user) throws ValidationException {
		shift.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		shift.setStatus(stDao.findByStatus("Inactive"));
		shift.setUpdatedBy(user);
		shiftDao.save(shift);
	}
	
	public Shift findByNonBk(Time masuk, Time pulang) throws ValidationException {
		return shiftDao.findByNonBk(masuk, pulang);
	}
}
