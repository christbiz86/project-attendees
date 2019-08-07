package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ShiftProjectDao;
import com.attendee.attendee.model.ShiftProject;


@Service
public class ShiftProjectService {
	@Autowired
	private ShiftProjectDao shiftProjectDao;
	
	public void valIdNotExist(UUID id) throws ValidationException {
		if(shiftProjectDao.isExist(id)) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valIdExist(UUID id) throws ValidationException {
		if(!shiftProjectDao.isExist(id)) {
			throw new ValidationException("Data Tidak Ada");
		}
	}
	
	public void valIdNotNull(ShiftProject shiftProject)throws ValidationException {	
		if(shiftProject.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public ShiftProject findById(UUID id) throws ValidationException {
		return shiftProjectDao.findById(id);
	}
	
	public ShiftProject findByBk(ShiftProject shiftProject) {
		return shiftProjectDao.findByBk(shiftProject.getShift().getId(), shiftProject.getProject().getId());
	}
	
	public List<ShiftProject> findAll() throws ValidationException {
		return shiftProjectDao.findAll();
	}
	
	public void valBkNotExist(ShiftProject shiftProject) throws ValidationException {
		if(shiftProjectDao.isBkExist(shiftProject.getShift().getId(), shiftProject.getProject().getId())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valBkNotChange(ShiftProject shiftProject) throws ValidationException {
		String project = findById(shiftProject.getId()).getProject().getKode();
		String shift = findById(shiftProject.getId()).getShift().getKode();
		if(!shiftProject.getShift().getKode().toString().equals(shift.toString())) {
			throw new ValidationException("Kode Shift Tidak Boleh Diubah");
		}
		if(!shiftProject.getProject().getKode().toString().equals(project.toString())) {
			throw new ValidationException("Kode Project Tidak Boleh Diubah");
		}
	}
	
	public void valBkNotNull(ShiftProject shiftProject) throws ValidationException {
		if(shiftProject.getProject() == null) {
			throw new ValidationException("Kode Project Tidak Boleh Kosong");
		}
		
		if(shiftProject.getShift() == null) {
			throw new ValidationException("Kode Shift Tidak Boleh Kosong");
		}
	}
	
	public void save(ShiftProject shiftProject) throws ValidationException {
		valBkNotNull(shiftProject);
		valBkNotExist(shiftProject);
		shiftProjectDao.save(shiftProject);
	}
	
	public void update(ShiftProject shiftProject) throws ValidationException {
		valIdNotNull(shiftProject);
		valIdExist(shiftProject.getId());
		valBkNotNull(shiftProject);
		valBkNotChange(shiftProject);
		shiftProjectDao.save(shiftProject);
	}
	
	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		shiftProjectDao.delete(id);
	}
}
