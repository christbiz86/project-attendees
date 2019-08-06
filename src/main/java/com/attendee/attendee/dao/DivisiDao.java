package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Divisi;

@Repository
public class DivisiDao extends ParentDao{
	
	@Transactional
	public void save(Divisi divisi) {
		super.entityManager.merge(divisi);
	}
	
	@Transactional
	public void delete(UUID id) {
		Divisi divisi = findById(id);
		super.entityManager.remove(divisi);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Divisi findById(UUID id) {	
		
		List<Divisi> list = super.entityManager
                .createQuery("from Divisi where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Divisi();
		}
		else {
			return (Divisi)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Divisi> findAll() {	
		
		List<Divisi> list = super.entityManager
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
	public Divisi findByBk(String divisi) {	
		
		List<Divisi> list = super.entityManager
                .createQuery("from Divisi where divisi=:divisi")
                .setParameter("divisi", divisi)
                .getResultList();

		if (list.size() == 0) {
			return new Divisi();
		}
		else {
			return (Divisi)list.get(0);
		}
	}

	public boolean isBkExist(String divisi) {
		
		if(findByBk(divisi).getId()==null) {
	
			return false;
		}else {
			return true;
		}	 
	}

}
