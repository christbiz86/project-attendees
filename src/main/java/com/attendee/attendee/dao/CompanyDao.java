package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Company;

@Repository
public class CompanyDao extends ParentDao {
	@SuppressWarnings("unchecked")
	@Transactional
	public Company findById(UUID id) {
		List<Company> list = super.entityManager
				.createQuery("FROM Company"
						+ "WHERE id = :id")
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
	public Company findByBk(String kode) {
		List<Company> list = super.entityManager
				.createQuery("FROM Company"
						+ "WHERE kode = :kode")
				.setParameter("kode", kode)
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
	public Company findByFilter(String nama) {
		List<Company> list = super.entityManager
				.createQuery("FROM Company"
						+ "WHERE nama = :nama")
				.setParameter("nama", nama)
				.getResultList();
		
		if (list.size() == 0) {
			return new Company();
		}
		else {
			return (Company)list.get(0);
		}
				
	}
	
	@Transactional
	public boolean isIdExist(UUID id) {
		if(findById(id).getId() == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@Transactional
	public boolean isBkExist(String kode) {
		if(findByBk(kode).getKode() == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Company> findAll() {
		List<Company> list = super.entityManager
				.createQuery("FROM Company")
				.getResultList();
		return list;
	}
	
	@Transactional
	public void save(Company company) {
		super.entityManager.merge(company);
	}
	
	@Transactional
	public void update(Company company) {
		super.entityManager.merge(company);
	}
	
	@Transactional
	public void delete(UUID id) {
		Company company = findById(id);
		super.entityManager.remove(company);
	}

}
