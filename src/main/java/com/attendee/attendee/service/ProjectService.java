package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ProjectDao;
import com.attendee.attendee.model.Project;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao projectDao;
	
	public void valIdNotExist(UUID id) throws ValidationException {
		if(projectDao.isExist(id)) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valIdExist(UUID id) throws ValidationException {
		if(!projectDao.isExist(id)) {
			throw new ValidationException("Data Tidak Ada");
		}
	}
	
	public void valIdNotNull(Project project)throws ValidationException {	
		if(project.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(Project project) throws ValidationException {
		StringBuilder sb = new StringBuilder();
		int error = 0;
		if(project.getKode() == null) {
			sb.append("Kode Project Tidak Boleh Kosong");
		}
		
		if(project.getNamaProject() == null) {
			sb.append("Nama Project Tidak Boleh Kosong");
		}
		
		if(project.getLokasi() == null) {
			sb.append("Lokasi Project Tidak Boleh Kosong");
		}
		
		if(project.getIdStatus() == null) {
			sb.append("Status Project Tidak Boleh Kosong");
		}
		
		if(project.getCreatedAt() == null) {
			sb.append("Tanggal Pembuatan Project Tidak Boleh Kosong");
		}
		
		if(project.getCreatedBy() == null) {
			sb.append("Pembuat Project Tidak Boleh Kosong");
		}
		
		if(error > 0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public Project findById(UUID id) throws ValidationException {
		return projectDao.findById(id);
	}
	
	public Project findByBk(Project project) {
		return projectDao.findByBk(project.getKode());
	}
	
	public List<Project> findAll() throws ValidationException {
		return projectDao.findAll();
	}
	
	public void valBkNotExist(Project project) throws ValidationException {
		if(projectDao.isBkExist(project.getKode())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valBkNotChange(Project project) throws ValidationException {
		String s = findById(project.getId()).getKode();
		if(!project.getKode().toString().equals(s.toString())) {
			throw new ValidationException("Kode Project Tidak Boleh Diubah");
		}
	}
	
	public void valBkNotNull(Project project) throws ValidationException {
		if(project.getKode() == null) {
			throw new ValidationException("Kode Project Tidak Boleh Kosong");
		}
	}
	
	public void save(Project project) throws ValidationException {
		valBkNotNull(project);
		valBkNotExist(project);
		valNonBk(project);
		projectDao.save(project);
	}
	
	public void update(Project project) throws ValidationException {
		valIdNotNull(project);
		valIdExist(project.getId());
		valBkNotNull(project);
		valBkNotChange(project);
		valNonBk(project);
		projectDao.save(project);
	}
	
	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		projectDao.delete(id);
	}
}
