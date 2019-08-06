package com.attendee.attendee.service;

import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ShiftDao;
import com.attendee.attendee.model.Shift;

@Service
public class ShiftService {
	@Autowired ShiftDao shiftDao;
	
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
		
	}
	
	public void valBkNotExist(Shift shift) throws ValidationException {
		if(shiftDao.isBkExist(shift.getKode())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	
}
