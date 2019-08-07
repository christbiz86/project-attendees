package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UserShiftProjectDao;
import com.attendee.attendee.model.UserShiftProject;

@Service
public class UserShiftProjectService {
	@Autowired
	private UserShiftProjectDao userShiftProjectDao;
	
	public void valIdNotExist(UUID id) throws ValidationException {
		if(userShiftProjectDao.isExist(id)) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valIdExist(UUID id) throws ValidationException {
		if(!userShiftProjectDao.isExist(id)) {
			throw new ValidationException("Data Tidak Ada");
		}
	}
	
	public void valIdNotNull(UserShiftProject userShiftProject)throws ValidationException {	
		if(userShiftProject.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public UserShiftProject findById(UUID id) throws ValidationException {
		return userShiftProjectDao.findById(id);
	}
	
	public UserShiftProject findByBk(UserShiftProject shiftProject) {
		return userShiftProjectDao.findByBk(shiftProject.getUserCompany().getId(), shiftProject.getShiftProject().getId());
	}
	
	public List<UserShiftProject> findAll() throws ValidationException {
		return userShiftProjectDao.findAll();
	}
	
	public void valBkNotExist(UserShiftProject userShiftProject) throws ValidationException {
		if(userShiftProjectDao.isBkExist(userShiftProject.getUserCompany().getId(), userShiftProject.getShiftProject().getId())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valBkNotChange(UserShiftProject userShiftProject) throws ValidationException {
		UUID company= findById(userShiftProject.getId()).getUserCompany().getId();
		UUID shiftProject = findById(userShiftProject.getId()).getShiftProject().getId();
		if(!userShiftProject.getUserCompany().getId().toString().equals(company.toString())) {
			throw new ValidationException("Kolom Company Tidak Boleh Diubah");
		}
		if(!userShiftProject.getShiftProject().getId().toString().equals(shiftProject.toString())) {
			throw new ValidationException("Kolom Shift Project Tidak Boleh Diubah");
		}
	}
	
	public void valBkNotNull(UserShiftProject userShiftProject) throws ValidationException {
		if(userShiftProject.getUserCompany() == null) {
			throw new ValidationException("Kolom Company Tidak Boleh Kosong");
		}
		
		if(userShiftProject.getShiftProject() == null) {
			throw new ValidationException("Kolom Shift Project Tidak Boleh Kosong");
		}
	}
	
	public void valNonBk(UserShiftProject userShiftProject) throws ValidationException {
		StringBuilder sb = new StringBuilder();
		int error = 0;
		if(userShiftProject.getWorktime()==null) {
			sb.append("Worktime Tidak Boleh Kosong");
		}
		
		if(error > 0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void save(UserShiftProject userShiftProject) throws ValidationException {
		valBkNotNull(userShiftProject);
		valBkNotExist(userShiftProject);
		valNonBk(userShiftProject);
		userShiftProjectDao.save(userShiftProject);
	}
	
	public void update(UserShiftProject userShiftProject) throws ValidationException {
		valIdNotNull(userShiftProject);
		valIdExist(userShiftProject.getId());
		valBkNotNull(userShiftProject);
		valBkNotChange(userShiftProject);
		valNonBk(userShiftProject);
		userShiftProjectDao.save(userShiftProject);
	}
	
	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		userShiftProjectDao.delete(id);
	}
}
