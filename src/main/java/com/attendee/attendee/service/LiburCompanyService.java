package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.LiburCompanyDao;
import com.attendee.attendee.model.LiburCompany;

@Service
public class LiburCompanyService {

	@Autowired
	private LiburCompanyDao liburCompanyDao;
	
	private void valIdExist(UUID id) throws ValidationException {
		if(!liburCompanyDao.isExist(id)) {
			throw new ValidationException("Data Tidak Ada");
		}
	}
	
	private void valIdNotNull(LiburCompany liburCompany)throws ValidationException {	
		if(liburCompany.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	private void valBkNotExist(LiburCompany liburCompany) throws ValidationException {
		if(liburCompanyDao.isBkExist(liburCompany.getLibur().getId(), liburCompany.getCompany().getId())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	private void valNonBk(LiburCompany liburCompany) throws ValidationException {
		StringBuilder sb=new StringBuilder();
		int error=0;
		
		if(liburCompany.getLibur().getId().equals(null)) {
			sb.append("Hari Libur tidak boleh kosong \n");
			error++;
		}
		if(liburCompany.getCompany().getId().equals(null)) {
			sb.append("Company tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void insert(LiburCompany liburCompany)throws ValidationException {
		valNonBk(liburCompany);
		valBkNotExist(liburCompany);
		liburCompanyDao.save(liburCompany);
	}
	
	public void update(LiburCompany liburCompany)throws ValidationException {
		valIdNotNull(liburCompany);
		valIdExist(liburCompany.getId());
		valNonBk(liburCompany);
		liburCompanyDao.save(liburCompany);
	}
	
	public void delete(UUID id)throws ValidationException{
		valIdExist(id);
		liburCompanyDao.delete(id);
	}
	
	public LiburCompany findById(LiburCompany liburCompany) {
		return liburCompanyDao.findById(liburCompany.getId());
	}
	
	public List<LiburCompany> findByFilter(LiburCompany liburCompany) {
		return liburCompanyDao.findByFilter(liburCompany);
	}
	
	public LiburCompany findByBk(LiburCompany liburCompany) {
		return liburCompanyDao.findByBk(liburCompany.getLibur().getId(), liburCompany.getCompany().getId());
	}
	
	public List<LiburCompany> findAll() {
		return liburCompanyDao.findAll();
	}
	
	public List<LiburCompany> findByTgl(LiburCompany liburCompany) {
		return liburCompanyDao.findByTgl(liburCompany);
	}

}
