package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.CompanyUnitPosisiDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.CompanyUnitPosisi;

@Service
public class CompanyUnitPosisiService {
	
	@Autowired
	private CompanyUnitPosisiDao companyUnitPosisiDao;

	public void valIdExist(UUID id)throws ValidationException{
		
		if(!companyUnitPosisiDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	public void valIdNotNull(CompanyUnitPosisi companyUnitPosisi)throws ValidationException {
		
		if(companyUnitPosisi.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(CompanyUnitPosisi companyUnitPosisi)throws ValidationException{
		
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(companyUnitPosisi.getIdCompany().getId()==null) {
			sb.append("company tidak boleh kosong \n");
			error++;
		}
		if(companyUnitPosisi.getIdPosisi().getId()==null) {
			sb.append("posisi tidak boleh kosong \n");
			error++;
		}
		if(companyUnitPosisi.getIdUnit().getId()==null) {
			sb.append("unit tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void valBkNotExist(CompanyUnitPosisi companyUnitPosisi)throws ValidationException{
		if(companyUnitPosisiDao.isBkExist(companyUnitPosisi.getIdCompany().getId(),companyUnitPosisi.getIdUnit().getId(),companyUnitPosisi.getIdPosisi().getId())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	@Transactional
	public void insert(CompanyUnitPosisi companyUnitPosisi)throws ValidationException{
		
//		valBkNotExist(companyUnitPosisi);
		valNonBk(companyUnitPosisi);
		companyUnitPosisiDao.save(companyUnitPosisi);
	}
	
	@Transactional
	public void update(CompanyUnitPosisi companyUnitPosisi)throws ValidationException{
		
		valIdNotNull(companyUnitPosisi);
		valIdExist(companyUnitPosisi.getId());
		valNonBk(companyUnitPosisi);
		valBkNotExist(companyUnitPosisi);
		companyUnitPosisiDao.save(companyUnitPosisi);
	}
	
	public void delete(UUID id)throws ValidationException{
	
		valIdExist(id);
		companyUnitPosisiDao.delete(id);
	}
	
	public CompanyUnitPosisi findById(UUID id)throws ValidationException{

		return companyUnitPosisiDao.findById(id);
	}
		
	public List<CompanyUnitPosisi> findAll( )throws ValidationException{
		return companyUnitPosisiDao.findAll();
	}
	
	public List<CompanyUnitPosisi> findByFilter(CompanyUnitPosisi companyUnitPosisi){
		return companyUnitPosisiDao.findByFilter(companyUnitPosisi);
	}
	
	public CompanyUnitPosisi findByBk(UUID idCompany,UUID idUnit,UUID idPosisi) {
		return companyUnitPosisiDao.findByBk(idCompany, idUnit, idPosisi);
	}
	
	public void insertSuperAdmin(CompanyUnitPosisi companyUnitPosisi) throws ValidationException{
		
		companyUnitPosisiDao.save(companyUnitPosisi);
	}
	
	public CompanyUnitPosisi findByIdCompany(UUID idCompany) {
	
		return companyUnitPosisiDao.findByIdCompany(idCompany);
	
	}
}
