package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.UserShiftProject;

@Repository
public class UserShiftProjectDao extends ParentDao {
	@Transactional
	public void save(UserShiftProject userShiftProject) {
		super.entityManager.merge(userShiftProject);
	}
	
	@Transactional
	public void delete(UUID id) {
		UserShiftProject userShiftProject = findById(id);
		super.entityManager.remove(userShiftProject);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public UserShiftProject findById(UUID id) {	
		List<UserShiftProject> list = super.entityManager
                .createQuery("FROM UserShiftProject WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		}
		else {
			return (UserShiftProject)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserShiftProject> findAll() {	
		List<UserShiftProject> list = super.entityManager
                .createQuery("FROM UserShiftProject ")
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
	public UserShiftProject findByBk(String kode) {		
		List<UserShiftProject> list = super.entityManager
                .createQuery("FROM UserShiftProject WHERE kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		}
		else {
			return (UserShiftProject)list.get(0);
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
