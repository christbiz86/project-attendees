package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UserShiftProjectDao;
import com.attendee.attendee.model.UserShiftProject;

@Service
public class UserShiftProjectService {
	@Autowired
	private UserShiftProjectDao userShiftProjectDao;
	
	@Transactional
	public List<UserShiftProject> filterUserShift(String worktime) throws Exception {
		List<UserShiftProject> userShiftList = userShiftProjectDao.filterUserShift(worktime);
		if(userShiftList.size() == 0) {
			throw new ValidationException("Data Tidak Ditemukan");
		} else {
			return userShiftList;
		}
	}
	
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
	
	@Transactional
	public UserShiftProject findById(UUID id) throws ValidationException {
		return userShiftProjectDao.findById(id);
	}
	
	@Transactional
	public UserShiftProject findByBk(UserShiftProject shiftProject) {
		return userShiftProjectDao.findByBk(shiftProject.getUserCompany().getId(), shiftProject.getShiftProject().getId());
	}
	
	@Transactional
	public List<UserShiftProject> findAll() throws ValidationException {
		return userShiftProjectDao.findAll();
	}
	
	public void valBkNotExist(UserShiftProject userShiftProject) throws ValidationException {
		if(userShiftProjectDao.isBkExist(userShiftProject.getUserCompany().getId(), userShiftProject.getShiftProject().getId())) {
			throw new ValidationException("Data Sudah Ada");
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
		
		if(userShiftProject.getUserCompany() == null) {
			sb.append("Kolom Company Tidak Boleh Kosong");
			error++;
		}
		
		if(userShiftProject.getShiftProject() == null) {
			sb.append("Kolom Shift Project Tidak Boleh Kosong");
			error++;
		}
		
		if(error > 0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	@Transactional
	public void save(UserShiftProject userShiftProject) throws ValidationException {
		valBkNotNull(userShiftProject);
		valBkNotExist(userShiftProject);
		valNonBk(userShiftProject);
		userShiftProjectDao.save(userShiftProject);
	}
	
	@Transactional
	public void update(UserShiftProject userShiftProject) throws ValidationException {
		valIdNotNull(userShiftProject);
		valIdExist(userShiftProject.getId());
		valNonBk(userShiftProject);
		userShiftProjectDao.save(userShiftProject);
	}
	
	@Transactional
	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		userShiftProjectDao.delete(id);
	}
}
