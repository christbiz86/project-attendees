package com.attendee.attendee.dao;

import java.math.BigInteger;
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
				.createQuery("FROM Company "
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
				.createQuery("FROM Company "
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
	public Company findByFilter(Company company) {
		StringBuilder sb = new StringBuilder("SELECT c.id, c.kode, c.nama, c.jatah_cuti, c.toleransi_keterlambatan, c.id_status, ");
		sb.append("c.created_at, c.updated_at, c.created_by, c.updated_by, s.id, s.status ");
		sb.append("FROM company c ");
		sb.append("JOIN status s ");
		sb.append("ON c.id_status = s.id ");
		sb.append("WHERE 1=1 ");
		
		if(!company.getKode().equals("null")) {
			sb.append("AND c.kode LIKE '"+company.getKode()+"' ");
		}
		
		if(!company.getNama().equals("null")) {
			sb.append("AND c.nama LIKE '"+company.getNama()+"' ");
		}
		
		if(!company.getIdStatus().getStatus().equals("null")) {
			sb.append("AND s.status LIKE '"+company.getIdStatus().getStatus()+"' ");
		}
		
		List<Company> list = super.entityManager
				.createNativeQuery(sb.toString(), Company.class)
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
	public String countRows() {
		BigInteger count = (BigInteger) super.entityManager
				.createNativeQuery("SELECT count(*) FROM company").getSingleResult();
		
		int next = count.intValue() + 1;
		String num = Integer.toString(next);
		
		if(num.length() == 1) {
			return "00"+num;
		} else if(num.length() == 2) {
			return "0"+num;
		} else {
			return num;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Company findByName(String nama) {
		List<Company> list = super.entityManager
				.createQuery("FROM Company "
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
	

}
