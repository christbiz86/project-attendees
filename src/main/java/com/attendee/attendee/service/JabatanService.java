package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.JabatanDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Jabatan;

@Service
public class JabatanService {

	@Autowired
	private JabatanDao jabatanDao;
	
	
	public void valIdExist(UUID id)throws ValidationException{
		
		if(!jabatanDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	public void valIdNotNull(Jabatan jabatan)throws ValidationException {
		
		if(jabatan.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(Jabatan jabatan)throws ValidationException{
		
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(jabatan.getIdStatus()==null || jabatan.getIdStatus().getId()==null) {
			sb.append("status tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void valBkNotExist(Jabatan jabatan)throws ValidationException{
		if(jabatanDao.isBkExist(jabatan.getJabatan())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	public void valBkNotChange(Jabatan jabatan)throws ValidationException{
		String s=findById(jabatan.getId()).getJabatan();
		if(!jabatan.getJabatan().toString().equals(s.toString())) {

			throw new ValidationException("jabatan tidak boleh berubah");
		}
	}
	
	public void valBkNotNull(Jabatan jabatan) throws ValidationException{
		
		if(jabatan.getJabatan()==null) {

			throw new ValidationException("jabatan tidak boleh kosong");
		}
	}
	
	public void save(Jabatan jabatan)throws ValidationException{
		
		valBkNotNull(jabatan);
		valBkNotExist(jabatan);
		valNonBk(jabatan);
		jabatanDao.save(jabatan);
	}
	
	public void update(Jabatan jabatan)throws ValidationException{
		
		valIdNotNull(jabatan);
		valIdExist(jabatan.getId());
		valBkNotNull(jabatan);
		valBkNotChange(jabatan);
		valNonBk(jabatan);
		jabatanDao.save(jabatan);
	}
	
	public void delete(UUID id)throws ValidationException{
	
		valIdExist(id);
		jabatanDao.delete(id);
	}
	
	public Jabatan findById(UUID id)throws ValidationException{

		return jabatanDao.findById(id);
	}
	
	public Jabatan findByBk(Jabatan jabatan) {

		return jabatanDao.findByBk(jabatan.getJabatan());
	}
	
	public List<Jabatan> findAll( )throws ValidationException{
		return jabatanDao.findAll();
	}
	
}
