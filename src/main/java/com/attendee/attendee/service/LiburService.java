package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.LiburDao;
import com.attendee.attendee.model.Libur;

@Service
public class LiburService {

	@Autowired
	LiburDao liburDao;
	
	public void valIdNotExist(UUID id) throws ValidationException {
		if(liburDao.isExist(id)) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	
	public void valIdExist(UUID id) throws ValidationException {
		if(!liburDao.isExist(id)) {
			throw new ValidationException("Data Tidak Ada");
		}
	}
	
	public void valIdNotNull(Libur libur)throws ValidationException {	
		if(libur.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(Libur libur) throws ValidationException {
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(libur.getTglMulai()==null) {
			sb.append("tanggal mulai tidak boleh kosong \n");
			error++;
		}
		if(libur.getTglAkhir()==null) {
			sb.append("tanggal akhir tidak boleh kosong \n");
			error++;
		}
		if(libur.getStatus()==null) {
			sb.append("status tidak boleh kosong \n");
			error++;
		}
		if(libur.getCreatedAt()==null) {
			sb.append("tanggal dibuat tidak boleh kosong \n");
			error++;
		}
		if(libur.getCreatedBy()==null) {
			sb.append("pembuat tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	public void valBkNotExist(Libur libur) throws ValidationException {
		if(liburDao.isBkExist(libur.getNama())) {
			throw new ValidationException("Data Sudah Ada");
		}
	}
	public void valBkNotChange(Libur libur)throws ValidationException{
		String s=findById(libur.getId()).getNama();
		if(!libur.getNama().equals(s.toString())) {

			throw new ValidationException("Nama tidak boleh berubah");
		}
	}
	
	public void valBkNotNull(Libur libur) throws ValidationException{
		
		if(libur.getNama()==null) {

			throw new ValidationException("Nama tidak boleh kosong");
		}
	}
	
	public void save(Libur libur)throws ValidationException{
		
		valBkNotNull(libur);
		valBkNotExist(libur);
		valNonBk(libur);
		liburDao.save(libur);
	}
	
	public void update(Libur libur)throws ValidationException{
		
		valIdNotNull(libur);
		valIdExist(libur.getId());
		valBkNotNull(libur);
		valBkNotChange(libur);
		valNonBk(libur);
//		libur.setUpdatedAt();
//		libur.setUpdatedBy();
		liburDao.save(libur);
	}
	
	public void delete(UUID id)throws ValidationException{
	
		valIdExist(id);
		liburDao.delete(id);
	}
	
	public Libur findById(UUID id)throws ValidationException{

		return liburDao.findById(id);
	}
	
	public Libur findByBk(Libur libur) {

		return liburDao.findByBk(libur.getNama());
	}
	
	public List<Libur> findAll()throws ValidationException{
		return liburDao.findAll();
	}
}
