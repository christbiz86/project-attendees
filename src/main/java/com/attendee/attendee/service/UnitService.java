package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UnitDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Unit;

@Service
public class UnitService {

	@Autowired
	private UnitDao divisiDao;
	
	@Autowired
	private StatusService statusService;
	
	public void valIdExist(UUID id)throws ValidationException{
		
		if(!divisiDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	public void valIdNotNull(Unit divisi)throws ValidationException {
		
		if(divisi.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valNonBk(Unit divisi)throws ValidationException{
		
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
	
	public void valBkNotExist(Unit divisi)throws ValidationException{
		if(divisiDao.isBkExist(divisi.getUnit())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	public void valBkNotChange(Unit divisi)throws ValidationException{
		String s=findById(divisi.getId()).getUnit();
		if(!divisi.getUnit().toString().equals(s.toString())) {

			throw new ValidationException("divisi tidak boleh berubah");
		}
	}
	
	public void valBkNotNull(Unit divisi) throws ValidationException{
		
		if(divisi.getUnit()==null) {

			throw new ValidationException("divisi tidak boleh kosong");
		}
	}
	
	private void valCreatedNotChange(Unit unit)throws ValidationException {
		Unit tempUnit=findById(unit.getId());
			
		if(tempUnit.getCreatedAt()!=unit.getCreatedAt() && tempUnit.getCreatedBy().getId()!=unit.getCreatedBy().getId()) {
			throw new ValidationException("created tidak boleh berubah");
		}
	}
	
	public void save(Unit divisi)throws ValidationException{
		divisi.setIdStatus(statusService.findByStatus("Active"));
		divisi.setCreatedAt(getTime());
		divisi.setUpdatedAt(null);
		divisi.setUpdatedBy(null);
		
		valBkNotNull(divisi);
		valBkNotExist(divisi);
		valNonBk(divisi);
		divisiDao.save(divisi);
	}
	
	public void update(Unit divisi)throws ValidationException{
		Unit tempUnit=findById(divisi.getId());
		divisi.setCreatedAt(tempUnit.getCreatedAt());
		divisi.setCreatedBy(tempUnit.getCreatedBy());
		divisi.setUpdatedAt(getTime());
		divisi.setIdStatus(statusService.findByStatus(divisi.getIdStatus().getStatus()));
		valCreatedNotChange(divisi);
		
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
	
	public Unit findById(UUID id)throws ValidationException{

		return divisiDao.findById(id);
	}
	
	public Unit findByBk(Unit divisi) {

		return divisiDao.findByBk(divisi.getUnit());
	}
	
	public List<Unit> findAll( )throws ValidationException{
		return divisiDao.findAll();
	}
	
	public List<Unit> findByFilter(Unit unit)throws ValidationException{
		return divisiDao.findByFilter(unit);
	}
	
	private Timestamp getTime() {
		return  new Timestamp(System.currentTimeMillis());
	}
}
