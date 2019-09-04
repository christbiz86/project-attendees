package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.PosisiDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Posisi;

@Service
public class PosisiService {

	@Autowired
	private PosisiDao jabatanDao;
	
	@Autowired
	private StatusService statusService;
	
	private void valIdExist(UUID id)throws ValidationException{
		
		if(!jabatanDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	private void valIdNotNull(Posisi jabatan)throws ValidationException {
		
		if(jabatan.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	private void valNonBk(Posisi jabatan)throws ValidationException{
		
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
	
	private void valBkNotExist(Posisi jabatan)throws ValidationException{
		if(jabatanDao.isBkExist(jabatan.getPosisi())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	private void valBkNotChange(Posisi jabatan)throws ValidationException{
		String s=findById(jabatan.getId()).getPosisi();
		if(!jabatan.getPosisi().equals(s)) {

			throw new ValidationException("jabatan tidak boleh berubah");
		}
	}
	
	private void valBkNotNull(Posisi jabatan) throws ValidationException{
		
		if(jabatan.getPosisi()==null) {

			throw new ValidationException("jabatan tidak boleh kosong");
		}
	}
	
	private void valCreatedNotChange(Posisi posisi)throws ValidationException {
		Posisi tempPosisi=findById(posisi.getId());
			
		if(tempPosisi.getCreatedAt()!=posisi.getCreatedAt() && tempPosisi.getCreatedBy()!=posisi.getCreatedBy()) {
			throw new ValidationException("created tidak boleh berubah");
		}
	
	}
	
	public void save(Posisi jabatan)throws ValidationException{
		jabatan.setCreatedAt(getTime());
		jabatan.setUpdatedAt(null);
		jabatan.setUpdatedBy(null);
		jabatan.setIdStatus(statusService.findByStatus("Active"));
		
		valBkNotNull(jabatan);
		valBkNotExist(jabatan);
		valNonBk(jabatan);
		jabatanDao.save(jabatan);
	}
	
	public void update(Posisi jabatan)throws ValidationException{
		jabatan.setUpdatedAt(getTime());
		
		valCreatedNotChange(jabatan);
		
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
	
	public Posisi findById(UUID id)throws ValidationException{

		return jabatanDao.findById(id);
	}
	
	public Posisi findByBk(Posisi jabatan) {

		return jabatanDao.findByBk(jabatan.getPosisi());
	}
	
	public List<Posisi> findAll( )throws ValidationException{
		return jabatanDao.findAll();
	}
	
	public List<Posisi> findByFilter(Posisi posisi)throws ValidationException{
		return jabatanDao.findByFilter(posisi);
	}
	
	private Timestamp getTime() {
		return  new Timestamp(System.currentTimeMillis());
	}
}
