package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Approval;

@Repository
public class ApprovalDao extends ParentDao {
	@Transactional
	public void save(Approval approval) {
		super.entityManager.merge(approval);
	}
	
	@Transactional
	public void delete(Approval approval) {
		super.entityManager.remove(approval);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Approval findById(UUID id) {
		List<Approval> list = super.entityManager
				.createQuery("FROM Approval WHERE id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size() == 0) {
			return new Approval();
		} else {
			return list.get(0);
		}
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Approval> findAll() {
		List<Approval> list = super.entityManager
                .createQuery(" from Approval ")
                .getResultList();
		if (list.size() == 0)
			return new ArrayList<Approval>();
		else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Approval findByBk(String kode) {	
		
		List<Approval> list = super.entityManager
                .createQuery("from Approval where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Approval();
		}
		else {
			return (Approval)list.get(0);
		}
	}
	
	public boolean isIdExist(UUID id) {
		if(findById(id).getId().equals(null)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isBkExist(String kode) {
		if(findByBk(kode).getId() == null) {
			return false;
		} else {
			return true;
		}
	}
}
