package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
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
	
	@Transactional
	public ShiftProject findById(UUID id) throws ValidationException {
		return shiftProjectDao.findById(id);
	}
	
	@Transactional
	public ShiftProject findByBk(ShiftProject shiftProject) {
		return shiftProjectDao.findByBk(shiftProject.getShift().getId(), shiftProject.getProject().getId());
	}
	
	@Transactional
	public List<ShiftProject> findAll() throws ValidationException {
		return shiftProjectDao.findAll();
	}
	
	public void valBkNotExist(ShiftProject shiftProject) throws ValidationException {
		if(shiftProjectDao.isBkExist(shiftProject.getShift().getId(), shiftProject.getProject().getId())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valNonBk(ShiftProject shiftProject) throws ValidationException {
		if(shiftProject.getProject() == null) {
			throw new ValidationException("Kode Project Tidak Boleh Kosong");
		}
		
		if(shiftProject.getShift() == null) {
			throw new ValidationException("Kode Shift Tidak Boleh Kosong");
		}
	}
	
	@Transactional
	public void save(ShiftProject shiftProject) throws ValidationException {
		valNonBk(shiftProject);
		valBkNotExist(shiftProject);
		shiftProjectDao.save(shiftProject);
	}
	
	@Transactional
	public void update(ShiftProject shiftProject) throws ValidationException {
		valIdNotNull(shiftProject);
		valIdExist(shiftProject.getId());
		valNonBk(shiftProject);
		shiftProjectDao.save(shiftProject);
	}
	
	@Transactional
	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		shiftProjectDao.delete(id);
	}
}
