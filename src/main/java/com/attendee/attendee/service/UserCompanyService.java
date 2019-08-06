package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UserCompanyDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.UserCompany;

@Service
public class UserCompanyService {

	@Autowired
	private UserCompanyDao userCompanyDao;
	
	
	public void valIdExist(UUID id)throws ValidationException{
		
		if(!userCompanyDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	public void valIdNotNull(UserCompany userCompany)throws ValidationException {
		
		if(userCompany.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(UserCompany userCompany)throws ValidationException{
		
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(userCompany.getIdCompanyDivisiJabatan()==null || userCompany.getIdCompanyDivisiJabatan().getId()==null) {
			sb.append("company divisi jabatan tidak boleh kosong \n");
			error++;
		}
		if(userCompany.getIdTipeUser()==null) {
			sb.append("tipe user tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void valBkNotExist(UserCompany userCompany)throws ValidationException{
		if(userCompanyDao.isBkExist(userCompany.getIdUser().getId().toString())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	public void valBkNotChange(UserCompany userCompany)throws ValidationException{
		String s=findById(userCompany.getId()).getIdUser().getId().toString();
		if(!userCompany.getIdUser().getId().toString().equals(s.toString())) {

			throw new ValidationException("user tidak boleh berubah");
		}
	}
	
	public void valBkNotNull(UserCompany userCompany) throws ValidationException{
		
		if(userCompany.getIdUser().getId()==null) {

			throw new ValidationException("user tidak boleh kosong");
		}
	}
	
	public void save(UserCompany userCompany)throws ValidationException{
		
		valBkNotNull(userCompany);
		valBkNotExist(userCompany);
		valNonBk(userCompany);
		userCompanyDao.save(userCompany);
	}
	
	public void update(UserCompany userCompany)throws ValidationException{
		
		valIdNotNull(userCompany);
		valIdExist(userCompany.getId());
		valBkNotNull(userCompany);
		valBkNotChange(userCompany);
		valNonBk(userCompany);
		userCompanyDao.save(userCompany);
	}
	
	public void delete(UUID id)throws ValidationException{
	
		valIdExist(id);
		userCompanyDao.delete(id);
	}
	
	public UserCompany findById(UUID id)throws ValidationException{

		return userCompanyDao.findById(id);
	}
	
	public UserCompany findByBk(UserCompany userCompany) {

		return userCompanyDao.findByBk(userCompany.getIdUser().getId().toString());
	}
	
	public List<UserCompany> findAll( )throws ValidationException{
		return userCompanyDao.findAll();
	}
}
