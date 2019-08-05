package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Jabatan;

@Repository
public class JabatanDao extends ParentDao{
	
	@Transactional
	public void save(Jabatan jabatan) {
		super.entityManager.merge(jabatan);
	}
	
	@Transactional
	public void delete(UUID id) {
		Jabatan jabatan = findById(id);
		super.entityManager.remove(jabatan);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Jabatan findById(UUID id) {	
		
		List<Jabatan> list = super.entityManager
                .createQuery("from Jabatan where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Jabatan();
		}
		else {
			return (Jabatan)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Jabatan> findAll() {	
		
		List<Jabatan> list = super.entityManager
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
	public Jabatan findByBk(String kode) {	
		
		List<Jabatan> list = super.entityManager
                .createQuery("from Jabatan where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Jabatan();
		}
		else {
			return (Jabatan)list.get(0);
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
