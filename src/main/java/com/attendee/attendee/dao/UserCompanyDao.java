package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.UserCompany;

@Repository
public class UserCompanyDao extends ParentDao{

	@Transactional
	public void save(UserCompany userCompany) {
		super.entityManager.merge(userCompany);
	}
	
	@Transactional
	public void delete(UUID id) {
		UserCompany userCompany = findById(id);
		super.entityManager.remove(userCompany);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public UserCompany findById(UUID id) {	
		
		List<UserCompany> list = super.entityManager
                .createQuery("from UserCompany where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		}
		else {
			return (UserCompany)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserCompany> findAll() {	
		
		List<UserCompany> list = super.entityManager
                .createQuery("from UserCompany ")
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
	public UserCompany findByBk(String kode) {	
		
		List<UserCompany> list = super.entityManager
                .createQuery("from UserCompany where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		}
		else {
			return (UserCompany)list.get(0);
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
