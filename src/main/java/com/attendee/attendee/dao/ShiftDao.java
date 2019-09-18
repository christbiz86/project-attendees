package com.attendee.attendee.dao;

import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Shift;

@Repository
public class ShiftDao extends ParentDao {
	@Autowired
	private StatusDao stDao;
	
	@Transactional
	public void save(Shift shift) {
		super.entityManager.merge(shift);
	}
	
//	@Transactional
//	public void delete(UUID id) {
//		Shift shift = findById(id);
//		super.entityManager.remove(shift);
//	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Shift findById(UUID id) {	
		
		List<Shift> list = super.entityManager
                .createQuery("FROM Shift WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Shift();
		}
		else {
			return (Shift)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Shift> findAll() {	
		
		List<Shift> list = super.entityManager
                .createQuery("FROM Shift ")
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
	public Shift findByBk(String kode) {	
		
		List<Shift> list = super.entityManager
                .createQuery("FROM Shift WHERE kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Shift();
		}
		else {
			return (Shift)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Shift> filterShift(String status){
		List<Shift> shiftList = super.entityManager
				.createQuery("FROM Shift WHERE status.status=:status")
				.setParameter("status", status)
				.getResultList();
		if(shiftList.size() == 0) {
			return new ArrayList<Shift>();
		} else {
			return shiftList;
		}
	}

	public boolean isBkExist(String kode) {
		
		if(findByBk(kode).getId()==null) {
			
			return false;
		}else {
			return true;
		}	 
	}
	
	@Transactional
	public String countRows() {
		BigInteger count = (BigInteger) super.entityManager
				.createNativeQuery("SELECT count(*) FROM shift").getSingleResult();
		
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
	
	@Transactional
	public void delete(UUID id) {
		StringBuilder sb = new StringBuilder("UPDATE SET status = '"+stDao.findByStatus("Inactive")+"' ");
		sb.append("WHERE id = "+id+"';");
		
		super.entityManager.createNativeQuery(sb.toString(), Shift.class);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Shift findByNonBk(Time masuk, Time pulang, String status) {
		List<Shift> list = super.entityManager
                .createQuery("FROM Shift WHERE masuk = :masuk AND pulang = :pulang AND status.status = :status")
                .setParameter("masuk", masuk)
                .setParameter("pulang", pulang)
                .setParameter("status", status)
                .getResultList();

		if (list.size() == 0) {
			return new Shift();
		}
		else {
			return (Shift)list.get(0);
		}
	}
	
}
