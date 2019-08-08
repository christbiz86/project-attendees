package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.Date;
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
	public List<Libur> findByFilter(Libur libur) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Libur WHERE 1=1");
		if(libur.getNama() != null) {
			String nama = libur.getNama();
			sb.append(" AND nama='"+nama+"'");
		}
		
		if (libur.getTglMulai() != null) {
			Date tglMulai = libur.getTglMulai();
			sb.append(" AND tglMulai='"+tglMulai+"'");
		}
		
		if (libur.getTglAkhir() != null) {
			Date tglAkhir = libur.getTglAkhir();
			sb.append(" AND tglAkhir='"+tglAkhir+"'");
		}
		
		if (libur.getStatus() != null) {
			String status = libur.getStatus().getStatus();
			sb.append(" AND status.status='"+status+"'");
		}

		List<Libur> list = super.entityManager
                .createQuery(sb.toString())
                .getResultList();

		if (list.size() == 0) {
			List<Libur> nullList = new ArrayList<Libur>();
			return nullList;
		}
		else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Libur findByBk(String nama) {		
		List<Libur> list = super.entityManager
                .createQuery("FROM Libur WHERE nama=:nama")
                .setParameter("nama", nama)
                .getResultList();

		if (list.size() == 0) {
			return new Libur();
		}
		else {
			return (Libur)list.get(0);
		}
	}

	public boolean isBkExist(String nama) {
		if(findByBk(nama).getId()==null) {
			return false;
		}else {
			return true;
		}	 
	}
}
