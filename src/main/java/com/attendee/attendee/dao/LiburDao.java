package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Libur;

@Repository
public class LiburDao extends ParentDao {
	@Transactional
	public void save(Libur libur) {
		super.entityManager.merge(libur);
	}
	
	@Transactional
	public void delete(UUID id) {
		Libur libur = findById(id);
		super.entityManager.remove(libur);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Libur findById(UUID id) {	
		List<Libur> list = super.entityManager
                .createQuery("FROM Libur WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Libur();
		}
		else {
			return (Libur)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Libur> findAll() {	
		List<Libur> list = super.entityManager
                .createQuery("FROM Libur ")
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
	public Libur findByBk(String kode) {		
		List<Libur> list = super.entityManager
                .createQuery("FROM Libur WHERE kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Libur();
		}
		else {
			return (Libur)list.get(0);
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
