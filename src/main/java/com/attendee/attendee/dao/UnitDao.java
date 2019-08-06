package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Unit;

@Repository
public class UnitDao extends ParentDao{
	
	@Transactional
	public void save(Unit divisi) {
		super.entityManager.merge(divisi);
	}
	
	@Transactional
	public void delete(UUID id) {
		Unit divisi = findById(id);
		super.entityManager.remove(divisi);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Unit findById(UUID id) {	
		
		List<Unit> list = super.entityManager
                .createQuery("from Divisi where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Unit();
		}
		else {
			return (Unit)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Unit> findAll() {	
		
		List<Unit> list = super.entityManager
                .createQuery("from Divisi ")
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
	public Unit findByBk(String kode) {	
		
		List<Unit> list = super.entityManager
                .createQuery("from Divisi where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Unit();
		}
		else {
			return (Unit)list.get(0);
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
