package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ShiftDao;
import com.attendee.attendee.model.Shift;

@Service
public class ShiftService {
	@Autowired 
	private ShiftDao shiftDao;
	
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
	
	public void valNonBk(Shift shift) throws ValidationException {
		StringBuilder sb = new StringBuilder();
		int error = 0;
		if(shift.getKode()==null) {
			sb.append("Kode Shift Tidak Boleh Kosong");
		}
		
		if(shift.getMasuk()==null) {
			sb.append("Jam Masuk Tidak Boleh Kosong ");
		}
		
		if(shift.getPulang()==null) {
			sb.append("Jam Pulang Tidak Boleh Kosong");
		}
		
		if(shift.getStatus()==null) {
			sb.append("Status Shift Tidak Boleh Kosong");
		}
		
		if(shift.getCreatedAt()==null) {
			sb.append("Tanggal Pembuatan Shift Tidak Boleh Kosong");
		}
		
		if(shift.getCreatedBy()==null) {
			sb.append("Pembuat Shift Tidak Boleh Kosong");
		}
		
		if(error > 0) {
			throw new ValidationException(sb.toString());
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
	
	public void save(Shift shift) throws ValidationException {
		valBkNotNull(shift);
		valBkNotExist(shift);
		valNonBk(shift);
		shiftDao.save(shift);
	}
	
	public void update(Shift shift) throws ValidationException {
		valIdNotNull(shift);
		valIdExist(shift.getId());
		valBkNotNull(shift);
		valBkNotChange(shift);
		valNonBk(shift);
		shiftDao.save(shift);
	}
	
	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		shiftDao.delete(id);
	}
	
	public Shift findById(UUID id) throws ValidationException {
		return shiftDao.findById(id);
	}
	
	public Shift findByBk(Shift shift) {
		return shiftDao.findByBk(shift.getKode());
	}
	
	public List<Shift> findAll() throws ValidationException {
		return shiftDao.findAll();
	}
}
