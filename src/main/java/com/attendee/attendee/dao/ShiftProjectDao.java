package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.ShiftProject;

@Repository
public class ShiftProjectDao extends ParentDao {
	@Transactional
	public void save(ShiftProject shiftProject) {
		super.entityManager.merge(shiftProject);
	}
	
	@Transactional
	public void delete(UUID id) {
		ShiftProject shiftProject = findById(id);
		super.entityManager.remove(shiftProject);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public ShiftProject findById(UUID id) {	
		List<ShiftProject> list = super.entityManager
                .createQuery("FROM ShiftProject WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new ShiftProject();
		}
		else {
			return (ShiftProject)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ShiftProject> findAll() {	
		List<ShiftProject> list = super.entityManager
                .createQuery("FROM ShiftProject ")
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
	public ShiftProject findByBk(String kode) {		
		List<ShiftProject> list = super.entityManager
                .createQuery("FROM ShiftProject WHERE kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new ShiftProject();
		}
		else {
			return (ShiftProject)list.get(0);
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
