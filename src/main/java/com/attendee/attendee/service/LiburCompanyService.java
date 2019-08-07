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
	
	private void valBkNotChange(LiburCompany liburCompany)throws ValidationException{
		UUID company=findById(liburCompany.getId()).getCompany().getId();
		UUID libur=findById(liburCompany.getId()).getLibur().getId();
		if(!liburCompany.getLibur().getId().toString().equals(libur.toString())) {

			throw new ValidationException("Hari Libur tidak boleh berubah");
		}
		if(!liburCompany.getCompany().getId().toString().equals(company.toString())) {

			throw new ValidationException("Company tidak boleh berubah");
		}
	}
	
	private void valBkNotNull(LiburCompany liburCompany) throws ValidationException{
		
		if(liburCompany.getLibur().getId()==null) {

			throw new ValidationException("Hari Libur tidak boleh kosong");
		}
		if(liburCompany.getCompany().getId()==null) {

			throw new ValidationException("Company tidak boleh kosong");
		}
	}
	
	public void save(LiburCompany liburCompany)throws ValidationException{
		
		valBkNotNull(liburCompany);
		valBkNotExist(liburCompany);
		liburCompanyDao.save(liburCompany);
	}
	
	public void update(LiburCompany liburCompany)throws ValidationException{
		
		valIdNotNull(liburCompany);
		valIdExist(liburCompany.getId());
		valBkNotNull(liburCompany);
		valBkNotChange(liburCompany);
		liburCompanyDao.save(liburCompany);
	}
	
	public void delete(UUID id)throws ValidationException{
	
		valIdExist(id);
		liburCompanyDao.delete(id);
	}
	
	public LiburCompany findById(UUID id)throws ValidationException{

		return liburCompanyDao.findById(id);
	}
	
	public LiburCompany findByBk(LiburCompany liburCompany) {

		return liburCompanyDao.findByBk(liburCompany.getLibur().getId(), liburCompany.getCompany().getId());
	}
	
	public List<LiburCompany> findAll()throws ValidationException{
		return liburCompanyDao.findAll();
	}
}
