package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.LiburDao;
import com.attendee.attendee.dao.StatusDao;
import com.attendee.attendee.model.Libur;
import com.attendee.attendee.model.Project;
import com.attendee.attendee.model.UserPrinciple;

@Service
public class LiburService {

	@Autowired
	LiburDao liburDao;
	
	@Autowired
	private StatusDao stDao;
	
	@Autowired
	private StatusService stService;
	
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

		if(libur.getNama()==null) {
			sb.append("Nama tidak boleh kosong \n");
			error++;
		}
		if(libur.getTglMulai()==null) {
			sb.append("tanggal mulai tidak boleh kosong \n");
			error++;
		}
		if(libur.getTglAkhir()==null) {
			sb.append("tanggal akhir tidak boleh kosong \n");
			error++;
		}
//		if(libur.getStatus()==null) {
//			sb.append("status tidak boleh kosong \n");
//			error++;
//		}
		if(libur.getCreatedAt()==null) {
			sb.append("tanggal dibuat tidak boleh kosong \n");
			error++;
		}
//		if(libur.getCreatedBy()==null) {
//			sb.append("pembuat tidak boleh kosong \n");
//			error++;
//		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	private void valCreatedNotChange(Libur libur)throws ValidationException {
		Libur tempLibur = findById(libur);
		
		if(tempLibur.getCreatedAt() != libur.getCreatedAt() && tempLibur.getCreatedBy().getId() != libur.getCreatedBy().getId()) {
			throw new ValidationException("Created tidak boleh berubah!");
		}
	}
	
	public void save(Libur libur)throws ValidationException{
		
		libur.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//		libur.setCreatedBy(createdBy);
		libur.setStatus(stDao.findByStatus("Active"));
		libur.setCreatedBy(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdUser());
		valNonBk(libur);
		liburDao.save(libur);
	}
	
	public void update(Libur libur)throws ValidationException{
		Libur tempLibur = findById(libur);
		libur.setCreatedAt(tempLibur.getCreatedAt());
		libur.setCreatedBy(tempLibur.getCreatedBy());
		libur.setStatus(stService.findByStatus(libur.getStatus().getStatus()));
		libur.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		libur.setUpdatedBy(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdUser());
		valCreatedNotChange(libur);
		valIdNotNull(libur);
		valIdExist(libur.getId());
		valNonBk(libur);
		liburDao.save(libur);
	}
	
	public void delete(Libur libur)throws ValidationException{
	
		valIdExist(libur.getId());
		liburDao.delete(libur.getId());
	}
	
	public Libur findById(Libur libur)throws ValidationException{

		return liburDao.findById(libur.getId());
	}
	
	public Libur findByBk(Libur libur) {

		return liburDao.findByBk(libur.getNama());
	}
	
	public List<Libur> findByFilter(Libur libur) {

		return liburDao.findByFilter(libur);
	}
	
	public List<Libur> findAll()throws ValidationException{
		return liburDao.findAll();
	}
}
