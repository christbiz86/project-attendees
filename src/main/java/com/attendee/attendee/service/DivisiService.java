package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.DivisiDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Divisi;

@Service
public class DivisiService {

	@Autowired
	private DivisiDao divisiDao;
	
	
	public void valIdExist(UUID id)throws ValidationException{
		
		if(!divisiDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	public void valIdNotNull(Divisi divisi)throws ValidationException {
		
		if(divisi.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(Divisi divisi)throws ValidationException{
		
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(divisi.getIdStatus()==null || divisi.getIdStatus().getId()==null) {
			sb.append("status tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void valBkNotExist(Divisi divisi)throws ValidationException{
		if(divisiDao.isBkExist(divisi.getDivisi())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	public void valBkNotChange(Divisi divisi)throws ValidationException{
		String s=findById(divisi.getId()).getDivisi();
		if(!divisi.getDivisi().toString().equals(s.toString())) {

			throw new ValidationException("divisi tidak boleh berubah");
		}
	}
	
	public void valBkNotNull(Divisi divisi) throws ValidationException{
		
		if(divisi.getDivisi()==null) {

			throw new ValidationException("divisi tidak boleh kosong");
		}
	}
	
	public void save(Divisi divisi)throws ValidationException{
		
		valBkNotNull(divisi);
		valBkNotExist(divisi);
		valNonBk(divisi);
		divisiDao.save(divisi);
	}
	
	public void update(Divisi divisi)throws ValidationException{
		
		valIdNotNull(divisi);
		valIdExist(divisi.getId());
		valBkNotNull(divisi);
		valBkNotChange(divisi);
		valNonBk(divisi);
		divisiDao.save(divisi);
	}
	
	public void delete(UUID id)throws ValidationException{
	
		valIdExist(id);
		divisiDao.delete(id);
	}
	
	public Divisi findById(UUID id)throws ValidationException{

		return divisiDao.findById(id);
	}
	
	public Divisi findByBk(Divisi divisi) {

		return divisiDao.findByBk(divisi.getDivisi());
	}
	
	public List<Divisi> findAll( )throws ValidationException{
		return divisiDao.findAll();
	}
	
}
