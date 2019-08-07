package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Posisi;

@Repository
public class PosisiDao extends ParentDao{
	
	@Transactional
	public void save(Posisi jabatan) {
		super.entityManager.merge(jabatan);
	}
	
	@Transactional
	public void delete(UUID id) {
		Posisi jabatan = findById(id);
		super.entityManager.remove(jabatan);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Posisi findById(UUID id) {	
		
		List<Posisi> list = super.entityManager
                .createQuery("from Jabatan where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Posisi();
		}
		else {
			return (Posisi)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Posisi> findAll() {	
		
		List<Posisi> list = super.entityManager
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
	public Posisi findByBk(String kode) {	
		
		List<Posisi> list = super.entityManager
                .createQuery("from Jabatan where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Posisi();
		}
		else {
			return (Posisi)list.get(0);
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
	public List<Posisi> findByFilter(Posisi posisi) {
		StringBuilder sb=new StringBuilder("SELECT p.id,p.posisi,p.id_status,p.created_at,p.updated_at,p.created_by,p.updated_by ");
		sb.append("FROM posisi p ");
		sb.append(" WHERE 1=1 ");		

		if(!posisi.getPosisi().equals(null)) {
			sb.append(" AND p.posisi LIKE '%"+posisi.getPosisi()+"%' ");
		}
		if(!posisi.getIdStatus().getId().equals(null)) {
			sb.append(" AND p.id_status LIKE '%"+posisi.getIdStatus().getId()+"%' ");
		}
		
		List<Posisi> list = super.entityManager.createNativeQuery(sb.toString(),Posisi.class).getResultList();
		
		if(list.size() == 0) {
			return new ArrayList<Posisi>();
		}else {
			return list;
		}
	}

}
