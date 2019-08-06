package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Company;

@Repository
public class CompanyDao extends ParentDao{

	@Transactional
	public void save(Company company) {
		super.entityManager.merge(company);
	}
	
	@Transactional
	public void delete(UUID id) {
		Company company = findById(id);
		super.entityManager.remove(company);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Company findById(UUID id) {	
		
		List<Company> list = super.entityManager
                .createQuery("from Company where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Company();
		}
		else {
			return (Company)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Company> findAll() {	
		
		List<Company> list = super.entityManager
                .createQuery("from Company ")
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
	public Company findByBk(String kode) {	
		
		List<Company> list = super.entityManager
                .createQuery("from Company where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Company();
		}
		else {
			return (Company)list.get(0);
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
