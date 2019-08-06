package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Shift;

@Repository
public class ShiftDao extends ParentDao {
	@Transactional
	public void save(Shift shift) {
		super.entityManager.merge(shift);
	}
	
	@Transactional
	public void delete(UUID id) {
		Shift shift = findById(id);
		super.entityManager.remove(shift);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Shift findById(UUID id) {	
		
		List<Shift> list = super.entityManager
                .createQuery("FROM Shift WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Shift();
		}
		else {
			return (Shift)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Shift> findAll() {	
		
		List<Shift> list = super.entityManager
                .createQuery("FROM Shift ")
                .getResultList();

		return list;
	}
	
	public boolean isExist(UUID id) {
		
		if(findById(id).getId()==null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Shift findByBk(String kode) {	
		
		List<Shift> list = super.entityManager
                .createQuery("FROM Shift WHERE kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Shift();
		}
		else {
			return (Shift)list.get(0);
		}
	}

	public boolean isBkExist(String kode) {
		
		if(findByBk(kode).getId()==null) {
			
			return false;
		}else {
			return true;
		}	 
	}
}
