package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Posisi;

@Repository
public class PosisiDao extends ParentDao{
	
	@Transactional
	public void save(Posisi jabatan) {
		super.entityManager.merge(jabatan);
	}
	
	@Transactional
	public void delete(UUID id) {
		Posisi jabatan = findById(id);
		super.entityManager.remove(jabatan);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Posisi findById(UUID id) {	
		
		List<Posisi> list = super.entityManager
                .createQuery("from Jabatan where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Posisi();
		}
		else {
			return (Posisi)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Posisi> findAll() {	
		
		List<Posisi> list = super.entityManager
                .createQuery("from Jabatan ")
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
	public Posisi findByBk(String kode) {	
		
		List<Posisi> list = super.entityManager
                .createQuery("from Jabatan where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Posisi();
		}
		else {
			return (Posisi)list.get(0);
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
