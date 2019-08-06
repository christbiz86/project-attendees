package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.CompanyDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Company;

@Service
public class CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	
	public void valIdExist(UUID id)throws ValidationException{
		
		if(!companyDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	public void valIdNotNull(Company company)throws ValidationException {
		
		if(company.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(Company company)throws ValidationException{
		
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(company.getNama()==null) {
			sb.append("nama tidak boleh kosong \n");
			error++;
		}
		if(company.getToleransiKeterlambatan()==null) {
			sb.append("keterlambatan tidak boleh kosong \n");
			error++;
		}
		if(company.getJatahCuti()==null) {
			sb.append("jatah cuti tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void valBkNotExist(Company company)throws ValidationException{
		if(companyDao.isBkExist(company.getKode())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	public void valBkNotChange(Company company)throws ValidationException{
		String s=findById(company.getId()).getKode();
		if(!company.getKode().toString().equals(s.toString())) {

			throw new ValidationException("kode tidak boleh berubah");
		}
	}
	
	public void valBkNotNull(Company company) throws ValidationException{
		
		if(company.getKode()==null) {

			throw new ValidationException("kode tidak boleh kosong");
		}
	}
	
	public void save(Company company)throws ValidationException{
		
		valBkNotNull(company);
		valBkNotExist(company);
		valNonBk(company);
		companyDao.save(company);
	}
	
	public void update(Company company)throws ValidationException{
		
		valIdNotNull(company);
		valIdExist(company.getId());
		valBkNotNull(company);
		valBkNotChange(company);
		valNonBk(company);
		companyDao.save(company);
	}
	
	public void delete(UUID id)throws ValidationException{
	
		valIdExist(id);
		companyDao.delete(id);
	}
	
	public Company findById(UUID id)throws ValidationException{

		return companyDao.findById(id);
	}
	
	public Company findByBk(Company company) {

		return companyDao.findByBk(company.getKode());
	}
	
	public List<Company> findAll( )throws ValidationException{
		return companyDao.findAll();
	}
	
	public List<Company> findByFilter(Company company){
		return companyDao.findByFilter(company);
	}
}
