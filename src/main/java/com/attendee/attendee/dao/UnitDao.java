package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Unit;

@Repository
public class UnitDao extends ParentDao {
	
	@Transactional
	public void save(Unit divisi) {
		super.entityManager.merge(divisi);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Unit findById(UUID id) {
		List<Unit> list = super.entityManager
                .createQuery("from Unit where id = :id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Unit();
		}
		else {
			return (Unit)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Unit> findAll() {
		List<Unit> list = super.entityManager
                .createQuery("from Divisi ")
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
	public Unit findByBk(String kode) {
		List<Unit> list = super.entityManager
                .createQuery("from Unit where kode = :kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Unit();
		}
		else {
			return (Unit)list.get(0);
		}
	}

	public boolean isBkExist(String kode) {
		if(findByBk(kode).getId()==null) {
	
			return false;
		}else {
			return true;
		}	 
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Unit> findByFilter(Unit unit) {
		StringBuilder sb=new StringBuilder("SELECT u.id,u.unit,u.id_status,u.created_at,u.updated_at,u.created_by,u.updated_by ");
		sb.append("FROM unit u ");
		sb.append("WHERE 1=1 ");		

		if(!unit.getUnit().equals(null)) {
			sb.append(" AND u.unit LIKE '%"+unit.getUnit()+"%' ");
		}
		if(!unit.getIdStatus().getStatus().equals(null)) {
			sb.append(" AND u.id_status LIKE '%"+unit.getIdStatus().getStatus()+"%' ");
		}
					
		List<Unit> list=super.entityManager.createNativeQuery(sb.toString(),Unit.class).getResultList();
		
		if(list.size()==0) {
			return new ArrayList<Unit>();
		}else {
			return list;
		}
	}

}
