package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.CompanyUnitPosisi;

@Repository
public class CompanyUnitPosisiDao extends ParentDao{

	@Transactional
	public void save(CompanyUnitPosisi companyUnitPosisi) {
		super.entityManager.merge(companyUnitPosisi);
	}
	
	@Transactional
	public void delete(UUID id) {
		CompanyUnitPosisi companyUnitPosisi = findById(id);
		super.entityManager.remove(companyUnitPosisi);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public CompanyUnitPosisi findById(UUID id) {	
		
		List<CompanyUnitPosisi> list = super.entityManager
                .createQuery("from CompanyUnitPosisi where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new CompanyUnitPosisi();
		}
		else {
			return (CompanyUnitPosisi)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<CompanyUnitPosisi> findAll() {	
		
		List<CompanyUnitPosisi> list = super.entityManager
                .createQuery("from CompanyUnitPosisi ")
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
	public CompanyUnitPosisi findByBk(UUID idCompany,UUID idUnit,UUID idPosisi) {	
		System.out.println("cek bk");
		List<CompanyUnitPosisi> list = super.entityManager
                .createQuery("from CompanyUnitPosisi where idCompany.id=:idCompany and idUnit.id=:idUnit and idPosisi.id=:idPosisi ")
                .setParameter("idCompany", idCompany)
                .setParameter("idUnit", idUnit)
                .setParameter("idPosisi", idPosisi)
                .getResultList();

		if (list.size() == 0) {
			return new CompanyUnitPosisi();
		}
		else {
			return (CompanyUnitPosisi)list.get(0);
		}
	}

	public boolean isBkExist(UUID idCompany,UUID idUnit,UUID idPosisi) {
		
		if(findByBk( idCompany, idUnit, idPosisi).getId()==null) {
	
			return false;
		}else {
			return true;
		}	 
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<CompanyUnitPosisi> findByFilter(CompanyUnitPosisi companyUnitPosisi) {
		
		StringBuilder sb=new StringBuilder("SELECT * ");
		sb.append("FROM company_unit_posisi p ");
		sb.append(" WHERE 1=1 ");		

		if(companyUnitPosisi.getIdCompany().getId()!=null) {
			sb.append(" AND p.id_company LIKE '%"+companyUnitPosisi.getIdCompany().getId()+"%' ");
		}
		if(companyUnitPosisi.getIdPosisi().getId()!=null) {
			sb.append(" AND p.id_posisi LIKE '%"+companyUnitPosisi.getIdPosisi().getId()+"%' ");
		}
		if(companyUnitPosisi.getIdUnit().getId()!=null) {
			sb.append(" AND p.id_unit LIKE '%"+companyUnitPosisi.getIdUnit().getId()+"%' ");
		}
		
		List<CompanyUnitPosisi> list=super.entityManager.createNativeQuery(sb.toString(),CompanyUnitPosisi.class).getResultList();
		
		if(list.size()==0) {
			return new ArrayList<CompanyUnitPosisi>();
		}else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public CompanyUnitPosisi findByIdCompany(UUID idCompany) {	
		
		List<CompanyUnitPosisi> list = super.entityManager
                .createQuery("from CompanyUnitPosisi where idCompany.id=:idCompany ")
                .setParameter("idCompany", idCompany)
                .getResultList();

		if (list.size() == 0) {
			return new CompanyUnitPosisi();
		}
		else {
			return (CompanyUnitPosisi)list.get(0);
		}
	}

	
}
