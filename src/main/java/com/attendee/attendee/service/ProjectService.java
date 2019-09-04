package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ProjectDao;
import com.attendee.attendee.exception.InvalidDataException;
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
	
	public void valNonBk(Project project) throws InvalidDataException {
		List<String> listErr = new ArrayList<String>();
		if(project.getKode() == null) {
			listErr.add("Kode Project Tidak Boleh Kosong");
		}
		System.out.println(project.getNamaProject());
		if(project.getNamaProject() == null) {
			listErr.add("Nama Project Tidak Boleh Kosong");
		}
		if(project.getLokasi() == null) {
			listErr.add("Lokasi Project Tidak Boleh Kosong");
		}
		if(project.getStatus() == null) {
			listErr.add("Status Project Tidak Boleh Kosong");
		}
		if(project.getCreatedAt() == null) {
			listErr.add("Tanggal Pembuatan Project Tidak Boleh Kosong");
		}
		if(project.getCreatedBy() == null) {
			listErr.add("Pembuat Project Tidak Boleh Kosong");
		}
		
		if(!listErr.isEmpty()) {
			throw new InvalidDataException(listErr);
		}
	}
	
	@Transactional
	public Project findById(UUID id) throws ValidationException {
		return projectDao.findById(id);
	}
	
	@Transactional
	public Project findByBk(Project project) {
		return projectDao.findByBk(project.getKode());
	}
	
	@Transactional
	public List<Project> findAll() throws ValidationException {
		return projectDao.findAll();
	}
	
	@Transactional
	public List<Project> filterProject(String status, String lokasi) throws Exception {
		List<Project> proList = projectDao.filterProject(status, lokasi);
		if(proList.size() == 0) {
			throw new ValidationException("Data Tidak Ditemukan");
		} else {
			return proList;
		}
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
		if(project.getKode().equals(null)) {
			throw new ValidationException("Kode Project Tidak Boleh Kosong");
		}
	}
	
	@Transactional
	public void save(Project project) throws Exception {
		project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		
		valBkNotNull(project);
		valBkNotExist(project);
		valNonBk(project);
		projectDao.save(project);
	}
	
	@Transactional
	public void update(Project project) throws Exception {
		project.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		
		valIdNotNull(project);
		valIdExist(project.getId());
		valBkNotNull(project);
		valBkNotChange(project);
		valNonBk(project);
		projectDao.save(project);
	}
	
	@Transactional
	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		projectDao.delete(id);
	}
}
